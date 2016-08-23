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

anulakrMgr.controller('SurveyController', function SurveyController($scope) {
  $scope.questions = [
    {
      "label": "1",
      "text": "Jaka jest ogólna charakterystyka firmy w której prcujesz?",
      "options": [
        {
          "label": "A",
          "text": "Firma jest miejscem osobistego spotkania. Przypomina wielką rodzinę. Ludzie mocno się angażują."
        },
        {
          "label": "B",
          "text": "Dominującymi cechami firmy są energia i przedsiębiorczość. Ludzie chętnie podejmują ryzyko."
        },
        {
          "label": "C",
          "text": "W firmie liczą się przede wszystkim wyniki. Główną troską jest jak najlepsze wykonywanie zadań. Pracownicy są bardzo ambitni i nastawieni na osiągnięcia."
        },
        {
          "label": "D",
          "text": "W firmie obowiązuje ścisła hierarchia i kontrola. Tym, co robią ludzie, zazwyczaj rządzą formalne procedury."
        }
      ]
    },
    {
      "label": "2",
      "text": "Jaki jest styl przywództwa w firmie w której pracujesz?",
      "options": [
        {
          "label": "A",
          "text": "Przywództwo w firmie powszechnie utożsamia się ze służeniem radą i pomocą oraz roztaczaniem opieki."
        },
        {
          "label": "B",
          "text": "Przywództwo w firmie powszechnie utożsamia się z przedsiębiorczością, nowatorstwem i podejmowaniem ryzyka."
        },
        {
          "label": "C",
          "text": "Przywództwo w firmie powszechnie utożsamia się ze stanowczością, ekspansywnością, orientacją na wyniki."
        },
        {
          "label": "D",
          "text": "Przywództwo w firmie powszechnie utożsamia się z koordynowaniem, sprawnym organizowaniem, stwarzaniem harmonijnych warunków do osiągania dobrych wyników."
        }
      ]
    }
  ].map(function (question) {
    question.options
      .map(function (option) {
        option.actual = 0;
        option.expected = 0;
        return option;
      });
    return question;
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
