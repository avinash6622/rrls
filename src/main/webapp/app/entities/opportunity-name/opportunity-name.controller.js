(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('OpportunityNameController', OpportunityNameController);

    OpportunityNameController.$inject = ['OpportunityName', 'ParseLinks', 'AlertService', 'paginationConstants','entity','$http','$sce','$scope','$filter'];

    function OpportunityNameController(OpportunityName, ParseLinks, AlertService, paginationConstants,entity,$http,$sce,$scope,$filter) {

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
        vm.selectedSector = null;
        vm.sectorType = null;

        var myDate=new Date();

        $scope.currentYear = $filter('date')(myDate,'yyyy');

        vm.autoCompleteOptions = {
            minimumChars : 1,
            dropdownHeight : '200px',
            data : function(searchText) {
                return $http.get('api/opportunity-sector').then(
                    function(response) {
                        searchText = searchText.toLowerCase();



                        // ideally filtering should be done on the server
                        var states = _.filter(response.data,
                            function(state) {
                                return (state.sectorName).toLowerCase()
                                    .startsWith(searchText);

                            });

                       /*  return _.pluck(states, 'sectorType');*/
                        return states;
                    });
            },
            renderItem : function(item) {
                return {
                    value : item,
                    label : $sce.trustAsHtml("<p class='auto-complete'>"
                        + item.sectorName + "</p>")
                };
            },

            itemSelected : function(e) {


                vm.selectedSector = e;
                vm.opportunityNames.sectorType = e.item.sectorName;
                vm.opportunityNames.segment = e.item.sectorTypes;
                // state.airport = e.item;
            }
        }

        function save () {
            vm.isSaving = true;



            if (vm.opportunityNames.id !== null) {
                OpportunityName.update(vm.opportunityNames, onSaveSuccess, onSaveError);
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
