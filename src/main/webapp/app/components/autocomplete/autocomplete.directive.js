(function () {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .directive('autoComplete', AutoCompleteDirective);

        AutoCompleteDirective.$inject = ['$rootScope', '$timeout'];

    function AutoCompleteDirective($rootScope, $timeout) {
        return {
            require: 'ngModel',
            link: function (scope, iElement, iAttrs) {
            // console.log(scope);
            // console.log(iElement);
            // console.log(iAttrs);
            // console.log(scope[iAttrs.uiItems]);
                iElement.autocomplete({
                    source: function (request, response) {
                        response($.map(scope[iAttrs.uiItems], function (value, key) {
                            return {
                                label: value.oppName,
                                value: value.id
                            }
                        }));

                    },
                    select: function(event, ui) {
                    // console.log(ui);
                        $timeout(function() {
                          iElement.trigger('input');
                        }, 0);
                    }
                });
            }
        }
    }
})();
