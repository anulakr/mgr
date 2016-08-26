'use strict';

var anulakrMgr = angular.module('anulakrMgr', []);

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

anulakrMgr.factory('questions', function($http){
  return {
    list: function(callback){
      $http
        .get('/questions')
        .success(callback);
    }
  };
});

anulakrMgr.controller('SurveyController', function SurveyController($scope, questions) {

  questions.list(function(data) {
    $scope.questions = data
      .map(function (question) {
        question.options
          .map(function (option) {
            option.actual = 0;
            option.expected = 0;
            return option;
          });
        return question;
      });
  });

  $scope.onlyNumbers = /^\d+$/;

  $scope.sumAnswers = function (question, sumType) {
    return question.options
      .map(function (option) { return parseInt(option[sumType]) || 0; })
      .reduce(function (acc, v) { return acc + v; }, 0)
  };

  $scope.validAnswers  = function (question, sumType) {
    return $scope.sumAnswers(question, sumType) == 100
  }
});
