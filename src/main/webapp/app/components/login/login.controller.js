(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('LoginController', LoginController);
    LoginController.$inject = ['$rootScope', '$state', '$timeout', 'Auth', 'Principal','$scope','$filter','$location'];

    function LoginController ($rootScope, $state, $timeout, Auth, Principal,$scope,$filter,$location) {
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
        vm.roles = {};

        $scope.currentPath = $location.path();

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

        var myDate=new Date();

        $scope.currentYear = $filter('date')(myDate,'yyyy');
/*
        Principal.identity().then(function(account) {
           // console.log("njdsnnkskj--->",account);
            vm.roles = account;
           // console.log(vm.roles.authorities);

            if(Principal.isAuthenticated()) {


                if (vm.roles.authorities[1] == 'CIO') {
                    console.log("hii");
                    $state.go('strategy-master');
                }
                else {

                    if (vm.roles.authorities[1] == 'Admin') {

                        $state.go('user-management');
                      /!*  $state.go('blogs');*!/
                    }
                    else {
                        $state.go('home');
                    }

                }
            }
            });*/

           console.log("djfsjfjsd--->",vm.roles);

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
                    Principal.identity().then(function(account) {
                    /*    console.log("njdsnnkskj--->",account);
                        vm.roles = account;
                        console.log(vm.roles.authorities);
                        if(vm.roles.authorities[1] == 'CIO')
                        {
                            console.log("hii");
                           $state.go('strategy-master');
                        }
                        else{

                            if(vm.roles.authorities[1] == 'Admin')
                            {
                              /!*  $state.go('user-management');*!/
                                $state.go('blogs');
                            }
                            else{
                                $state.go('home');
                            }

                   }*/




                    });
                    $state.go('blogs');
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
