(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('ExternalResearchDetailController', ExternalResearchDetailController);

    ExternalResearchDetailController.$inject = ['OpportunityMaster','$stateParams', 'Principal','ExternalResearchAnalyst','$scope','$filter','$uibModal','$http','$sce','Upload'];

    function ExternalResearchDetailController(OpportunityMaster,$stateParams,Principal, ExternalResearchAnalyst,$scope,$filter,$uibModal,$http,$sce,Upload) {
        var vm = this;

        vm.opportunityMasters = [];
        vm.oppMasterId='';
        vm.upload = upload;
        vm.selectFile = selectFile;
        vm.loadFileContent = loadFileContent;
        vm.load = load;
        vm.loadAll = loadAll;
        vm.externalResearch;     
        vm.load($stateParams.id);
        
        vm.loadAll();
        
        
        function loadAll () {      	
      	  
      	  $scope.$on('authenticationSuccess', function() {
                getAccount();});          
      }
	  getAccount();

    function getAccount() {
        Principal.identity().then(function(account) {
            vm.account = account;
            vm.isAuthenticated = Principal.isAuthenticated;
       
        });

    }
       
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
         
         vm.autoCompleteOpportunity = {
                 minimumChars : 1,
                 dropdownHeight : '200px',

                 data : function(searchText) {
                     return $http.get('api/opportunity-masters/all').then(
                         function(response) {
                             searchText = searchText.toLowerCase();
                           //  console.log(searchText);
                            // console.log(response);


                             // ideally filtering should be done on the server
                             var states = _.filter(response.data,
                                 function(state) {
                                // console.log(state);
                                     return (state.oppName).toLowerCase()
                                         .startsWith(searchText);

                                 });

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
                 	vm.selectedName = e;
                     vm.name = e.item.oppName;
                     vm.opportunityName=e.item;
                     console.log(vm.opportunityName.id,'NAme');


                     OpportunityMaster.getSearchOpportunity(vm.opportunityName,onSuccess1,onError1);
                 }
             }
         
         function onSuccess1(data, headers){
        	 
                 vm.opportunityMasters = data;
                 vm.oppMasterId=vm.opportunityMasters[0].id;                
               
         }
          function onError1() {


          }
          
          function loadFileContent(fileID) {

              window.open('/download-external/' + fileID, '_blank');


           }
          
          
          function upload () {             

              var selectitem = $scope.selitem;
                  vm.isSaving = true;


                      Upload.upload({
                          url: 'api/external-upload',
                          data: {fileUploads: vm.externalResearch.fileUpload},
                          params: {oppId: vm.oppMasterId,filetype:'External',uploadfileName:vm.uploadfileName,externalId:vm.externalResearch.id}// {oppCode: inputData.oppCode, oppName: inputData.oppName, oppDescription: inputData.oppDescription, strategyMasterId: inputData.strategyMasterId.id}
                      }).then(function (resp) {

                          if(resp.status == 201) {
                              if(vm.externalResearch.fileUploads==null)
                              {
                                  vm.externalResearch.fileUploads = [];

                              }
                           //   console.log("gashgdjgasudgua",vm.opportunityMaster.fileUploads);

                          	vm.externalResearch.fileUploads.push(resp.data);
                          }
                      }, function (resp) {
                          console.log(resp);
                      }, function (evt) {
                          console.log(evt);

                      });
              }
          
          function selectFile (file) {



              vm.externalResearch.fileUpload = file;
          }
         
         
        function load(id) {
        
        	ExternalResearchAnalyst.get({id: id}, function(result) {
        		vm.externalResearch = result;
        		console.log(vm.externalResearch);
            });
        }
    }
})();
