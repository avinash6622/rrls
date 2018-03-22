(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('ResetFinishController', ResetFinishController);
      /*  .directive("compareTo1",compareTo1);*/

    ResetFinishController.$inject = ['$stateParams', '$timeout', 'Auth', 'LoginService','$scope'];

    function ResetFinishController ($stateParams, $timeout, Auth, LoginService,$scope) {
        var vm = this;

        vm.keyMissing = angular.isUndefined($stateParams.key);
        vm.confirmPassword = null;
        vm.doNotMatch = null;
        vm.error = null;
        vm.finishReset = finishReset;
        vm.login = LoginService.open;
        vm.resetAccount = {};
        vm.success = null;

        $timeout(function (){angular.element('#password').focus();});


        var myDate=new Date();

        $scope.currentYear = $filter('date')(myDate,'yyyy');

        function finishReset() {
            vm.doNotMatch = null;
            vm.error = null;
            if (vm.resetAccount.password !== vm.confirmPassword) {
                vm.doNotMatch = 'ERROR';
            } else {
                Auth.resetPasswordFinish({key: $stateParams.key, newPassword: vm.resetAccount.password}).then(function () {
                    vm.success = 'OK';
                }).catch(function () {
                    vm.success = null;
                    vm.error = 'ERROR';
                });
            }
        }
    }
/*    function compareTo1() {
        return {
            require: "ngModel",
            scope: {
                otherModelValue: "=compareTo1"
            },
            link: function(scope, element, attributes, ngModel) {

                ngModel.$validators.compareTo1 = function(modelValue) {
                    return modelValue == scope.otherModelValue;
                };

                scope.$watch("otherModelValue", function() {
                    ngModel.$validate();
                });
            }
        };
    };*/
})();
