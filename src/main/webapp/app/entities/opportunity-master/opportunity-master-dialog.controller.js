(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('OpportunityMasterDialogController', OpportunityMasterDialogController);

    OpportunityMasterDialogController.$inject = ['$timeout', '$scope', '$state', '$stateParams', '$http', 'entity', 'Upload', 'OpportunityMaster', 'StrategyMaster', 'OpportunityName'];

    function OpportunityMasterDialogController ($timeout, $scope, $state, $stateParams,$http, entity, Upload,  OpportunityMaster, StrategyMaster, OpportunityName) {
        var vm = this;

        vm.opportunityMaster = entity;
        vm.opportunityMaster.selectedoppContanct = vm.opportunityMaster.selectedoppContanct ? vm.opportunityMaster.selectedoppContanct : [{}];

        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.strategymasters = StrategyMaster.query();
        vm.opportunityNames = $scope.opportunityNames = OpportunityName.query();
        vm.upload = upload;
        vm.selectFile = selectFile;
       /* vm.saveDoc=saveDoc;*/
        vm.readOnly = false;


        console.log("SELECTED------>",vm.opportunityNames);

     // var name= vm.opportunityNames;
        vm.autoCompleteOptions = {
            minimumChars: 1,
            data: function (searchText) {
                return $http.get('api/opportunity-names')
                    .then(function (response) {
                        searchText = searchText.toUpperCase();

                        // ideally filtering should be done on the server
                        var states = _.filter(response.data, function (state) {
                            return state.oppName.startsWith(searchText);

                        });

                        return _.pluck(states, 'oppName');
                    });
            },
         /*   renderItem: function (item) {
                console.log(item);

            },*/

            itemSelected: function (e) {
                console.log(e);


              //  state.airport = e.item;
            }
        }







      $scope.addContact = function() {
          vm.opportunityMaster.selectedoppContanct.push({
            })
        }
      $scope.removeContact = function(contactToRemove) {
    	  var index = this.vm.opportunityMaster.selectedoppContanct.indexOf(contactToRemove);
    	  this.vm.opportunityMaster.selectedoppContanct.splice(index, 1);
        }
      $scope.getTotal = function(val1, val2) {
      	var result = parseFloat(val1) + parseFloat(val2);      	
      	/*result = (isNaN(result)) ?  (result) : '';*/
      	
      	return result;
      };
        // Editor options.
        $scope.options = {
            language: 'en',
            allowedContent: true,
            entities: false,
            entities_greek: false,
            entities_latin: false,
            entities_processNumerical: 'force',
            basicEntities: false,
            extraPlugins: 'htmlwriter'
        };

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $state.go($state.current.parent, {}, {reload: true});
        }

        function save () {
            vm.isSaving = true;


            console.log("ndksjangkjshagn",vm.opportunityMaster);
            if (vm.opportunityMaster.id !== null) {
                OpportunityMaster.update(vm.opportunityMaster, onSaveSuccess, onSaveError);
            } else {
                OpportunityMaster.save(vm.opportunityMaster, onSaveSuccess, onSaveError);
            }
        }
       /* function saveDoc () {
            vm.isSaving = true;
                OpportunityMaster.save(vm.fileDocSave, onSaveSuccess, onSaveError);
        }*/

        function onSaveSuccess (result) {
            $scope.$emit('researchRepositoryLearningSystemApp:opportunityMasterUpdate', result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        function upload () {
            vm.isSaving = true;
            if (vm.opportunityMaster.id !== null) {

            } else {
                var inputData = vm.opportunityMaster;
                console.log(inputData);

                //OpportunityMaster.saveWithUpload({inputData: vm.opportunityMaster, fileUpload: vm.opportunityMaster.fileUpload}, onSaveSuccess, onSaveError);
                Upload.upload({
                    url: 'api/opportunity-masters',
                    data: {fileUpload: vm.opportunityMaster.fileUpload},
                    params: inputData// {oppCode: inputData.oppCode, oppName: inputData.oppName, oppDescription: inputData.oppDescription, strategyMasterId: inputData.strategyMasterId.id}
                }).then(function (resp) {
                    onSaveSuccess(resp);
                }, function (resp) {
                    onSaveError(resp);
                }, function (evt) {
                    //var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                    //console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
                });
            }
        }


        function selectFile (file) {
            vm.opportunityMaster.fileUpload = file;
        }

        vm.datePickerOpenStatus.createdDate = false;
        vm.datePickerOpenStatus.updatedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }

        $scope.changeEditor = function () {
            //console.log(vm.opportunityMaster.htmlContent);
        };

        $scope.getStrategyMaster = function(strategyID) {
        	var match = vm.strategymasters.filter(function(item) {
        		return item.strategyMaster.id == strategyID;
        	});
        	console.log(match);
        	return match;
        }
    }
})();
