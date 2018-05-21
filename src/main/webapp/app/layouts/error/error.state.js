(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .config(stateConfig)
        .controller('accessdeniedController', accessdeniedController);


    stateConfig.$inject = ['$stateProvider'];
    accessdeniedController.$inject = ['$scope','$filter'];

    function stateConfig($stateProvider) {
        $stateProvider
            .state('error', {
                parent: 'app',
                url: '/error',
                data: {
                    authorities: [],
                    pageTitle: 'Error page!'
                },
                views: {
                    'content@': {
                        templateUrl: 'app/layouts/error/error.html'

                    }
                }
            })
            .state('accessdenied', {
                parent: 'app',
                url: '/accessdenied',
                data: {
                    authorities: []
                },
                views: {
                    'content@': {
                        templateUrl: 'app/layouts/error/accessdenied.html',
                        controller: 'accessdeniedController'
                    }
                }
            });
    }

    function accessdeniedController($scope,$filter){

        console.log("hi");

        var myDate = new Date();
        console.log($scope.currentYear);

        $scope.currentYear = $filter('date')(myDate, 'yyyy');



 }


})();


