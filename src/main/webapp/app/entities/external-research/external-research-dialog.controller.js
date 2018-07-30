(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('ExternalResearchDialogController', ExternalResearchDialogController);

    ExternalResearchDialogController.$inject = ['OpportunityName','$timeout', '$scope', '$state','$stateParams','$filter','entity', '$sce','$http','ExternalResearchAnalyst'];

    function ExternalResearchDialogController (OpportunityName,$timeout, $scope,$state, $stateParams,$filter, entity,$sce,$http, ExternalResearchAnalyst) {
        var vm = this;

        vm.externalResearch = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.clear = clear;
        vm.selectedSector = null;
        vm.sectorType = '';

        var myDate = new Date();
        
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
                   vm.externalResearch.sectorType = e.item.sectorName;
                   
                
                   // state.airport = e.item;
               }
           }
        
        function save () {
            vm.isSaving = true;
            if (vm.externalResearch.id !== null) {
            	ExternalResearchAnalyst.update(vm.externalResearch, onSaveSuccess, onSaveError);
            } else {
            	ExternalResearchAnalyst.save(vm.externalResearch, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('researchRepositoryLearningSystemApp:ExternalResearchAnalystUpdate', result);           
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.dateActive = false;
        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.updatedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
        
        function clear() {
			$state.go($state.current.parent, {}, {
				reload : true
			});
		}
    }
})();
