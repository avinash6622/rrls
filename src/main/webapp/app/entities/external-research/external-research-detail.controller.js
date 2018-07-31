(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('ExternalResearchDetailController', ExternalResearchDetailController);

    ExternalResearchDetailController.$inject = ['$stateParams', 'ExternalResearchAnalyst','$scope','$filter','$uibModal'];

    function ExternalResearchDetailController($stateParams, ExternalResearchAnalyst,$scope,$filter,$uibModal) {
        var vm = this;

        vm.load = load;
        vm.externalResearch;     
        vm.load($stateParams.id);
       
        var myDate=new Date();
        
         $scope.currentYear = $filter('date')(myDate,'yyyy');
         
         $scope.review = function(){

             var modalInstance = $uibModal.open({

                 templateUrl: 'app/entities/review-external-comment/external-reviews.html',
                 controllerAs: '$ctrl',
                 controller: 'ReviewExternalController',
                 size: 'lg',
                 resolve: {
                     options: function() {
                         return $stateParams;
                     }
                 }

             });

         }
         
        function load(id) {
        
        	ExternalResearchAnalyst.get({id: id}, function(result) {
        		vm.externalResearch = result;
        		console.log(vm.externalResearch);
            });
        }
    }
})();
