(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('OpportunityNameController', OpportunityNameController);

    OpportunityNameController.$inject = ['OpportunityName', 'ParseLinks', 'AlertService', 'paginationConstants','entity'];

    function OpportunityNameController(OpportunityName, ParseLinks, AlertService, paginationConstants,entity) {

        var vm = this;

        vm.opportunityNames = entity;

        vm.save = save;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.page = 0;
        vm.links = {
            last: 0
        };
        vm.predicate = 'id';
        vm.reverse = true;


        function save () {
            vm.isSaving = true;


            console.log("ndksjangkjshagn",vm.opportunityNames);
            if (vm.opportunityNames.id !== null) {
                OpportunityName.update(vm.opportunityMaster, onSaveSuccess, onSaveError);
            } else {
                OpportunityName.save(vm.opportunityNames, onSaveSuccess, onSaveError);
            }
        }


        function onSaveSuccess (result) {
           //$scope.$emit('researchRepositoryLearningSystemApp:opportunityMasterUpdate', result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }




    }
})();
