(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('NavbarController', NavbarController);

    NavbarController.$inject = ['$state', 'Auth', 'Principal', 'ProfileService', 'LoginService','$scope','OpportunityMaster','$http','DateUtils'];

    function NavbarController ($state, Auth, Principal, ProfileService, LoginService,$scope,OpportunityMaster,$http,DateUtils) {
        var vm = this;

        vm.isNavbarCollapsed = true;
        vm.isAuthenticated = Principal.isAuthenticated;

        ProfileService.getProfileInfo().then(function(response) {
            vm.inProduction = response.inProduction;
            vm.swaggerEnabled = response.swaggerEnabled;
        });
        vm.account = null;
        vm.login = login;
        vm.logout = logout;
        vm.toggleNavbar = toggleNavbar;
        vm.collapseNavbar = collapseNavbar;
        vm.$state = $state;
        vm.count = null;
        vm.close = close;
        vm.readstatus= readstatus;
        vm.nonotification = null;



        $scope.$on('authenticationSuccess', function() {
            getAccount();
        });

        getAccount();







        function getAccount() {
            Principal.identity().then(function(account) {
                vm.account = account;
                vm.isAuthenticated = Principal.isAuthenticated;


                $http.get('api/history-logs/'+vm.account.id)
                    .then(function(response) {
                        vm.notificationValues = [];
                        console.log("RESPONSE",response);


                        var len = response.data;

                        console.log(len.length);


                            for (var i = 0; i < len.length; i++) {
                                vm.notificationValues.push(len[i]);
                            }



                        console.log(vm.notificationValues);


                    });


            });

        }




        function login() {
            collapseNavbar();
         //   LoginService.open();
            $state.go('home');
        }

        function logout() {
            collapseNavbar();
            Auth.logout();
            $state.go('login');
        }

        function toggleNavbar() {
            vm.isNavbarCollapsed = !vm.isNavbarCollapsed;
        }

        function collapseNavbar() {
            vm.isNavbarCollapsed = true;
        }

        function close(id,userid,index,status){

            vm.notificationValues.splice(index, 1);
            console.log(id);
            console.log(userid);
            console.log(status);

         OpportunityMaster.notification({notiId :id,userId:userid,status:status}, function(resp){

         });


        }

        function readstatus(id,userid,status,item){
            console.log(id);
            console.log(userid);
            console.log(status);
            console.log(item);

            if (item.dStatus !== 'Read') {
                OpportunityMaster.notification({notiId :id,userId:userid,status:status}, function(resp){
                    console.log(resp);

                });
            }


        }

    }
})();
