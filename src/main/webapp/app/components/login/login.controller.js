(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('LoginController', LoginController);
    LoginController.$inject = ['$rootScope', '$state', '$timeout', 'Auth', 'Principal'];

    function LoginController ($rootScope, $state, $timeout, Auth, Principal) {
        var vm = this;

        vm.authenticationError = false;
        vm.cancel = cancel;
        vm.credentials = {};
        vm.login = login;
        vm.password = null;
        vm.register = register;
        vm.rememberMe = true;
        vm.requestResetPassword = requestResetPassword;
        vm.username = null;

        $timeout(function (){angular.element('#username').focus();});

          function cancel () {
            vm.credentials = {
                username: null,
                password: null,
                rememberMe: true
            };
            vm.authenticationError = false;
            if(Principal.isAuthenticated()) {
                   $state.go('home', {}, {reload: true});
                 }

         //   $uibModalInstance.dismiss('cancel');
        }

        function login (event) {
        	console.log('hi---->');
            event.preventDefault();
            Auth.login({
                username: vm.username,
                password: vm.password,
                rememberMe: vm.rememberMe
            }).then(function () {
                vm.authenticationError = false;
              //  $uibModalInstance.close();
                if ($state.current.name === 'register' || $state.current.name === 'activate' ||
                    $state.current.name === 'finishReset' || $state.current.name === 'requestReset') {
                	console.log('hello---->');
                    $state.go('home');
                }

                $rootScope.$broadcast('authenticationSuccess');

                // previousState was set in the authExpiredInterceptor before being redirected to login modal.
                // since login is successful, go to stored previousState and clear previousState
                if (Auth.getPreviousState()) {
                    var previousState = Auth.getPreviousState();
                    Auth.resetPreviousState();
                    $state.go(previousState.name, previousState.params);
                } else {
                	                  $state.go('home');
                	                 }

            }).catch(function () {
                vm.authenticationError = true;
            });
        }

        function register () {
         //   $uibModalInstance.dismiss('cancel');
            $state.go('register');
        }

        function requestResetPassword () {
          //  $uibModalInstance.dismiss('cancel');
            $state.go('requestReset');
        }
    }
})();
