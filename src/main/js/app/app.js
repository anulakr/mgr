'use strict';

var anulakrMgr = angular.module('anulakrMgr', ['ngResource']);

anulakrMgr.directive('numbersOnly', function () {
  return {
    require: 'ngModel',
    link: function (scope, element, attr, ngModelCtrl) {
      function fromUser(text) {
        if (text) {
          var transformedInput = text.replace(/[^0-9]/g, '');

          if (transformedInput !== text) {
            ngModelCtrl.$setViewValue(transformedInput);
            ngModelCtrl.$render();
          }
          return transformedInput;
        }
        return undefined;
      }
      ngModelCtrl.$parsers.push(fromUser);
    }
  };
});

anulakrMgr.factory('Question', ['$resource', function($resource) {
    return $resource('/questions');
  }
]);

anulakrMgr.factory('Survey', ['$resource', function($resource) {
  return $resource('/:company/surveys');
}
]);

anulakrMgr.controller('SurveyCtrl', function SurveyController($scope, Question, Survey) {

  $scope.surveySent = false;
  $scope.showErrors = false;

  $scope.comapny = "SoftwareMill";

  Question.query({}, function (data) {
    $scope.questions = data
      .map(function (question) {
        question.options
          .map(function (option) {
            option.current = 0;
            option.expected = 0;
            return option;
          });
        question.sumAnswers = function (sumType) {
          return question.options
            .map(function (option) { return parseInt(option[sumType]) || 0; })
            .reduce(function (acc, v) { return acc + v; }, 0);
        };
        question.validAnswers = function (sumType) {
          return this.sumAnswers(sumType) == 100;
        };
        question.isValid = function () {
          return this.validAnswers('current') && this.validAnswers('expected');
        };
        return question;
      });
    });

  $scope.validSurvey = function () {
    return $scope.questions.reduce(function (acc, question) {
      return acc && question.isValid();
    }, true)
  };

  $scope.sendSurvey = function () {
    if ($scope.validSurvey()) {
      var survey = new Survey({
        answers: $scope.questions.map(function (question) {
          return {
            question: question.label,
            current: question.options.reduce(function (map, option) {
              map[option.label] = parseInt(option.current) || 0;
              return map;
            }, {}),
            expected: question.options.reduce(function (map, option) {
              map[option.label] = parseInt(option.expected) || 0;
              return map;
            }, {})
          };
        })
      });
      survey.$save({company: $scope.comapny}, function () {
        $scope.surveySent = true;
      });
    } else {
      $scope.showErrors = true;
    }
  };

});
