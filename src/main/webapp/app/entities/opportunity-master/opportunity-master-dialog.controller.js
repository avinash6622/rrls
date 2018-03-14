(function() {
	'use strict';

	angular.module('researchRepositoryLearningSystemApp').controller(
			'OpportunityMasterDialogController',
			OpportunityMasterDialogController);

	OpportunityMasterDialogController.$inject = [ '$timeout', '$scope', '$sce',
			'$state', '$stateParams', '$http', 'entity', 'Upload',
			'OpportunityMaster', 'StrategyMaster', 'OpportunityName','$filter' ];

	function OpportunityMasterDialogController($timeout, $scope, $sce, $state,
			$stateParams, $http, entity, Upload, OpportunityMaster,
			StrategyMaster, OpportunityName, $filter) {
		var vm = this;

		vm.opportunityMaster = entity;
		vm.opportunityMaster.selectedoppContanct = vm.opportunityMaster.selectedoppContanct ? vm.opportunityMaster.selectedoppContanct
				: [ {} ];

		vm.clear = clear;
		vm.datePickerOpenStatus = {};
		vm.openCalendar = openCalendar;
		vm.opportunityMaster.financialSummaryData = vm.opportunityMaster.financialSummaryData ? vm.opportunityMaster.financialSummaryData
				: {};
		vm.opportunityMaster.nonFinancialSummaryData = vm.opportunityMaster.nonFinancialSummaryData ? vm.opportunityMaster.nonFinancialSummaryData
				: {};
		vm.save = save;
		vm.strategymasters = StrategyMaster.query();
		vm.opportunityNames = $scope.opportunityNames = OpportunityName.query();
		vm.upload = upload;
		vm.selectFile = selectFile;
        vm.selectFileData = selectFileData;
		vm.uploadfile = uploadfile;
		/* vm.saveDoc=saveDoc; */
		vm.readOnly = false;

		vm.selectedOpportunity = null;

		console.log("SELECTED------>", vm.opportunityNames);

		// var name= vm.opportunityNames;
		vm.autoCompleteOptions = {
			minimumChars : 1,
			dropdownHeight : '200px',
			data : function(searchText) {
				return $http.get('api/opportunity-names').then(
						function(response) {
                            searchText = searchText.toLowerCase();
                            console.log(searchText);


                          /*  var result= new RegExp(searchText, "i");

                            console.log(result);*/

						    /*var re =new RegExp(searchText,"i");


							searchText = re.ignoreCase;
*/
							// ideally filtering should be done on the server
							var states = _.filter(response.data,
									function(state) {
										return (state.oppName).toLowerCase()
												.startsWith(searchText);

									});

							/* return _.pluck(states, 'oppName'); */
							return states;
						});
			},
			renderItem : function(item) {
				return {
					value : item,
					label : $sce.trustAsHtml("<p class='auto-complete'>"
							+ item.oppName + "</p>")
				};
			},

			itemSelected : function(e) {
				console.log(e);

				vm.selectedOpportunity = e;
				vm.opportunityMaster.masterName = e.item;
				// state.airport = e.item;
			}
		}
		var myDate = new Date();


        var myDate = new Date();



        var previousYear = new Date(myDate);

        previousYear.setYear(myDate.getFullYear()-1);

        var previousYearBefore = new Date(previousYear);
        previousYearBefore.setYear(previousYear.getFullYear()-1);



        var nextYear = new Date(myDate);

        nextYear.setYear(myDate.getFullYear()+1);


        var nextYearNext = new Date(nextYear);

        nextYearNext.setYear(nextYear.getFullYear()+1);



        $scope.currentYear = $filter('date')(myDate,'yyyy');

        $scope.nextYear = $filter('date')(nextYear,'yyyy');

        $scope.prevYear = $filter('date')(previousYear,'yyyy');

        $scope.prevYearBefore = $filter('date')(previousYearBefore,'yyyy');

        $scope.neYearNext = $filter('date')(nextYearNext,'yyyy');




        $scope.addContact = function() {
			vm.opportunityMaster.selectedoppContanct.push({})
		}
		$scope.removeContact = function(contactToRemove) {
			var index = this.vm.opportunityMaster.selectedoppContanct
					.indexOf(contactToRemove);
			this.vm.opportunityMaster.selectedoppContanct.splice(index, 1);
		}
		$scope.getTotal = function(val1, val2, val3) {

			var result = parseFloat(val1) + parseFloat(val2);
			result = (isNaN(result)) ? '' : result;

			switch (val3) {
			case 1:
				vm.opportunityMaster.financialSummaryData.totIncOne = result;
				break;
			case 2:
				vm.opportunityMaster.financialSummaryData.totIncTwo = result;
				break;
			case 3:
				vm.opportunityMaster.financialSummaryData.totIncThree = result;
				break;
			case 4:
				vm.opportunityMaster.financialSummaryData.totIncFour = result;
				break;
			case 5:
				vm.opportunityMaster.financialSummaryData.totIncFive = result;
				break;
			default:
				break;
			}

			return result;
		};
		$scope.getFinPbv = function(val1, val2, val3) {

			var result = (parseFloat(val1) / parseFloat(val2)).toFixed(2);
			result = (isNaN(result)) ? '' : result;
			switch (val3) {
			case 1:
				vm.opportunityMaster.financialSummaryData.pbvOne = result;
				break;
			case 2:
				vm.opportunityMaster.financialSummaryData.pbvTwo = result;
				break;
			case 3:
				vm.opportunityMaster.financialSummaryData.pbvThree = result;
				break;
			case 4:
				vm.opportunityMaster.financialSummaryData.pbvFour = result;
				break;
			case 5:
				vm.opportunityMaster.financialSummaryData.pbvFive = result;
				break;
			case 6:
				vm.opportunityMaster.financialSummaryData.peOne = result;
				break;
			case 7:
				vm.opportunityMaster.financialSummaryData.peTwo = result;
				break;
			case 8:
				vm.opportunityMaster.financialSummaryData.peThree = result;
				break;
			case 9:
				vm.opportunityMaster.financialSummaryData.peFour = result;
				break;
			case 10:
				vm.opportunityMaster.financialSummaryData.peFive = result;
				break;
			default:
				break;
			}
			return result;
		};
		$scope.getFinRoe = function(val1, val2, val3, val4) {
			if (val2 == 0) {
				var result = parseFloat(val1) / parseFloat(val3);
				result = (result * 100).toFixed(2);
			} else {
				var result = parseFloat(val1)
						/ ((parseFloat(val2) + parseFloat(val3)) / 2);
				result = (result * 100).toFixed(2);
			}
			result = (isNaN(result)) ? '' : result;
			switch (val4) {
			case 1:
				vm.opportunityMaster.financialSummaryData.roeOne = result;
				break;
			case 2:
				vm.opportunityMaster.financialSummaryData.roeTwo = result;
				break;
			case 3:
				vm.opportunityMaster.financialSummaryData.roeThree = result;
				break;
			case 4:
				vm.opportunityMaster.financialSummaryData.roeFour = result;
				break;
			case 5:
				vm.opportunityMaster.financialSummaryData.roeFive = result;
				break;
			default:
				break;
			}
			return result;
		};
		$scope.getNonGrowth = function(val1, val2, val3) {

			var result = (parseFloat(val2) / parseFloat(val1)) - 1;
			result = (result * 100).toFixed(2);
			result = (isNaN(result)) ? '' : result;
			switch (val3) {
			case 1:
				vm.opportunityMaster.nonFinancialSummaryData.revGrowthTwo = result;
				break;
			case 2:
				vm.opportunityMaster.nonFinancialSummaryData.revGrowthThree = result;
				break;
			case 3:
				vm.opportunityMaster.nonFinancialSummaryData.revGrowthFour = result;
				break;
			case 4:
				vm.opportunityMaster.nonFinancialSummaryData.revGrowthFive = result;
				break;
			case 5:
				vm.opportunityMaster.nonFinancialSummaryData.ebitdaGrowthTwo = result;
				break;
			case 6:
				vm.opportunityMaster.nonFinancialSummaryData.ebitdaGrowthThree = result;
				break;
			case 7:
				vm.opportunityMaster.nonFinancialSummaryData.ebitdaGrowthFour = result;
				break;
			case 8:
				vm.opportunityMaster.nonFinancialSummaryData.ebitdaGrowthFive = result;
				break;
			case 9:
				vm.opportunityMaster.nonFinancialSummaryData.patGrowthTwo = result;
				break;
			case 10:
				vm.opportunityMaster.nonFinancialSummaryData.patGrowthThree = result;
				break;
			case 11:
				vm.opportunityMaster.nonFinancialSummaryData.patGrowthFour = result;
				break;
			case 12:
				vm.opportunityMaster.nonFinancialSummaryData.patGrowthFive = result;
				break;
			default:
				break;
			}
			return result;
		};
		$scope.getNonMargin = function(val1, val2, val3) {

			var result = parseFloat(val1) / parseFloat(val2);
			result = (result * 100).toFixed(2);
			result = (isNaN(result)) ? '' : result;
			switch (val3) {
			case 1:
				vm.opportunityMaster.nonFinancialSummaryData.marginOne = result;
				break;
			case 2:
				vm.opportunityMaster.nonFinancialSummaryData.marginTwo = result;
				break;
			case 3:
				vm.opportunityMaster.nonFinancialSummaryData.marginThree = result;
				break;
			case 4:
				vm.opportunityMaster.nonFinancialSummaryData.marginFour = result;
				break;
			case 5:
				vm.opportunityMaster.nonFinancialSummaryData.marginFive = result;
				break;
			default:
				break;
			}
			return result;
		};

		$scope.getNonPbt = function(val1, val2, val3, val4, val5) {

			var result = parseFloat(val1) + parseFloat(val2) - parseFloat(val3)
					- parseFloat(val4);
			result = (isNaN(result)) ? '' : result;
			switch (val5) {
			case 1:
				vm.opportunityMaster.nonFinancialSummaryData.pbtOne = result;
				break;
			case 2:
				vm.opportunityMaster.nonFinancialSummaryData.pbtTwo = result;
				break;
			case 3:
				vm.opportunityMaster.nonFinancialSummaryData.pbtThree = result;
				break;
			case 4:
				vm.opportunityMaster.nonFinancialSummaryData.pbtFour = result;
				break;
			case 5:
				vm.opportunityMaster.nonFinancialSummaryData.pbtFive = result;
				break;
			default:
				break;
			}
			return result;
		};

		$scope.getNonPat = function(val1, val2, val3) {

			var result = parseFloat(val1) - parseFloat(val2);
			result = (isNaN(result)) ? '' : result;
			switch (val3) {
			case 1:
				vm.opportunityMaster.nonFinancialSummaryData.patOne = result;
				break;
			case 2:
				vm.opportunityMaster.nonFinancialSummaryData.patTwo = result;
				break;
			case 3:
				vm.opportunityMaster.nonFinancialSummaryData.patthree = result;
				break;
			case 4:
				vm.opportunityMaster.nonFinancialSummaryData.patfour = result;
				break;
			case 5:
				vm.opportunityMaster.nonFinancialSummaryData.patFive = result;
				break;
			default:
				break;
			}
			return result;
		};
		$scope.getNonPe = function(val1, val2, val3) {

			var result = (parseFloat(val1) / parseFloat(val2)).toFixed(2);
			result = (isNaN(result)) ? '' : result;
			switch (val3) {
			case 1:
				vm.opportunityMaster.nonFinancialSummaryData.peOne = result;
				break;
			case 2:
				vm.opportunityMaster.nonFinancialSummaryData.peTwo = result;
				break;
			case 3:
				vm.opportunityMaster.nonFinancialSummaryData.pethree = result;
				break;
			case 4:
				vm.opportunityMaster.nonFinancialSummaryData.peFour = result;
				break;
			case 5:
				vm.opportunityMaster.nonFinancialSummaryData.peFive = result;
				break;
			case 6:
				vm.opportunityMaster.nonFinancialSummaryData.pbOne = result;
				break;
			case 7:
				vm.opportunityMaster.nonFinancialSummaryData.pbTwo = result;
				break;
			case 8:
				vm.opportunityMaster.nonFinancialSummaryData.pbThree = result;
				break;
			case 9:
				vm.opportunityMaster.nonFinancialSummaryData.pbFour = result;
				break;
			case 10:
				vm.opportunityMaster.nonFinancialSummaryData.pbFive = result;
				break;
			case 11:
				vm.opportunityMaster.nonFinancialSummaryData.deOne = result;
				break;
			case 12:
				vm.opportunityMaster.nonFinancialSummaryData.deTwo = result;
				break;
			case 13:
				vm.opportunityMaster.nonFinancialSummaryData.deThree = result;
				break;
			case 14:
				vm.opportunityMaster.nonFinancialSummaryData.deFour = result;
				break;
			case 15:
				vm.opportunityMaster.nonFinancialSummaryData.deFive = result;
				break;
			default:
				break;
			}
			return result;
		};
		$scope.getNonRoe = function(val1, val2, val3) {

			if (val2 == 0) {
				var result = parseFloat(val1) / parseFloat(val3);
				result = (result * 100).toFixed(2);
			} else {
				var result = parseFloat(val1)
						/ ((parseFloat(val2) + parseFloat(val3)) / 2);
				result = (result * 100).toFixed(2);
			}
			result = (isNaN(result)) ? '' : result;
			switch (val3) {
			case 1:
				vm.opportunityMaster.nonFinancialSummaryData.roeOne = result;
				break;
			case 2:
				vm.opportunityMaster.nonFinancialSummaryData.roeTwo = result;
				break;
			case 3:
				vm.opportunityMaster.nonFinancialSummaryData.roeThree = result;
				break;
			case 4:
				vm.opportunityMaster.nonFinancialSummaryData.roeFour = result;
				break;
			case 5:
				vm.opportunityMaster.nonFinancialSummaryData.roefive = result;
				break;
			default:
				break;
			}
			return result;
		};
		// Editor options.
		$scope.options = {
			language : 'en',
			allowedContent : true,
			entities : false,
			entities_greek : false,
			entities_latin : false,
			entities_processNumerical : 'force',
			basicEntities : false,
			extraPlugins : 'htmlwriter'
		};

		$timeout(function() {
			angular.element('.form-group:eq(1)>input').focus();
		});

		function clear() {
			$state.go($state.current.parent, {}, {
				reload : true
			});
		}

        function selectFileData (file) {
            console.log("File",file);

        }
		function uploadfile(){

		    console.log(vm.opportunityMaster.financialSummaryData);

            OpportunityMaster.upload(vm.opportunityMaster.financialSummaryData,vm.opportunityMaster.nonFinancialSummaryData)


        }
		function save() {
			vm.isSaving = true;

			console.log("ndksjangkjshagn", vm.opportunityMaster);
			console.log("Computation",
					vm.opportunityMaster.financialSummaryData);
			if (vm.opportunityMaster.id !== null) {
				OpportunityMaster.update(vm.opportunityMaster, onSaveSuccess,
						onSaveError);

			} else {
				OpportunityMaster.save(vm.opportunityMaster, onSaveSuccess,
						onSaveError);
			}
		}
		/*
		 * function saveDoc () { vm.isSaving = true;
		 * OpportunityMaster.save(vm.fileDocSave, onSaveSuccess, onSaveError); }
		 */

		function onSaveSuccess(result) {
			$scope
					.$emit(
							'researchRepositoryLearningSystemApp:opportunityMasterUpdate',
							result);
			vm.isSaving = false;
		}

		function onSaveError() {
			vm.isSaving = false;
		}

		function upload() {
			vm.isSaving = true;
			if (vm.opportunityMaster.id !== null) {

			} else {
				var inputData = vm.opportunityMaster;
				console.log(inputData);

				// OpportunityMaster.saveWithUpload({inputData:
				// vm.opportunityMaster, fileUpload:
				// vm.opportunityMaster.fileUpload}, onSaveSuccess,
				// onSaveError);
				Upload.upload({
					url : 'api/opportunity-masters',
					data : {
						fileUpload : vm.opportunityMaster.fileUpload
					},
					params : inputData
				// {oppCode: inputData.oppCode, oppName: inputData.oppName,
				// oppDescription: inputData.oppDescription, strategyMasterId:
				// inputData.strategyMasterId.id}
				}).then(function(resp) {
					onSaveSuccess(resp);
				}, function(resp) {
					onSaveError(resp);
				}, function(evt) {
					// var progressPercentage = parseInt(100.0 * evt.loaded /
					// evt.total);
					// console.log('progress: ' + progressPercentage + '% ' +
					// evt.config.data.file.name);
				});
			}
		}

		function selectFile(file) {
			vm.opportunityMaster.fileUpload = file;
		}

		vm.datePickerOpenStatus.createdDate = false;
		vm.datePickerOpenStatus.updatedDate = false;

		function openCalendar(date) {
			vm.datePickerOpenStatus[date] = true;
		}

		$scope.changeEditor = function() {
			// console.log(vm.opportunityMaster.htmlContent);
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
