var app = angular.module("angular-decimals", []);
app.directive("decimals", ['$filter', function ($filter) {
    return {
        restrict: "A", // Only usable as an attribute of another HTML element
        require: "?ngModel",
        scope: {
            decimals: "@"
        },
        link: function (scope, element, attr, ngModel) {
            var decimalCount = parseInt(scope.decimals) || 2;
            scope.$watch('decimals', function(newValue) {
                if (newValue !== undefined) {
                    decimalCount = parseInt(scope.decimals);
                    ngModel.$render();
                }
            });


            // Run when the model is first rendered and when the model is changed from code
            ngModel.$render = function() {
                /*console.log(ngModel.$modelValue);*/
                if (ngModel.$modelValue != null && ngModel.$modelValue) {
                    if (typeof decimalCount === "number") {
                        element.val(ngModel.$modelValue.toFixed(decimalCount).toString());
                    } else {
                        element.val(ngModel.$modelValue.toString());
                    }
                }
            }

            // Run when the view value changes - after each keypress
            // The returned value is then written to the model
            ngModel.$parsers.unshift(function(newValue) {
                if (typeof decimalCount === "number") {
                    var floatValue = parseFloat(newValue);
                    if (decimalCount === 0) {
                        return parseInt(floatValue);
                    }
                    return parseFloat(floatValue.toFixed(decimalCount));
                }

                return parseFloat(newValue);
            });

            // Formats the displayed value when the input field loses focus
            element.on("change", function(e) {
                var floatValue = parseFloat(element.val());
                if (!isNaN(floatValue) && typeof decimalCount === "number") {
                    if (decimalCount === 0) {
                        element.val(parseInt(floatValue));
                    } else {
                        var strValue = floatValue.toFixed(decimalCount);
                        element.val(strValue);
                    }
                }
            });
        }
    }
}]);

/* Number format Crore, Million */
var utilsApp = angular.module('Utils', []);
utilsApp.filter('thousandSuffix', function () {
    return function (input, decimals, multiples) {
       /* console.log(decimals);*/
        var exp, rounded,
            suffixes = ['Cr', 'M'],
            multiple = multiples || 1;


        if(window.isNaN(input)) {
            return '';
        }

        exp = Math.floor(Math.log(input) / Math.log(1000));

        return input != null ? (input.toFixed(decimals) * multiple).toLocaleString('en') : ''; // + ' ' + suffixes[exp - 1];
    };
});
