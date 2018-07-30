(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('ExternalResearchDetailController', ExternalResearchDetailController);

    ExternalResearchDetailController.$inject = ['$stateParams', 'ExternalResearchAnalyst','$scope','$filter'];

    function ExternalResearchDetailController($stateParams, ExternalResearchAnalyst,$scope,$filter) {
        var vm = this;

        vm.load = load;
        vm.externalResearch;     
        vm.load($stateParams.id);
       
        var myDate=new Date();
        
         $scope.currentYear = $filter('date')(myDate,'yyyy');
         
        function load(id) {
        
        	ExternalResearchAnalyst.get({id: id}, function(result) {
        		vm.externalResearch = result;
        		console.log(vm.externalResearch);
            });
        }
    }
})();
