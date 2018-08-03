(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('OpportunityLearningAIFController', OpportunityLearningAIFController);

    OpportunityLearningAIFController.$inject = ['OpportunityLearningAIF','LearningAIF', '$scope','Principal','ParseLinks', '$uibModalInstance', 'AlertService', 'paginationConstants','options'];

    function OpportunityLearningAIFController(OpportunityLearningAIF,LearningAIF,$scope, Principal,ParseLinks, $uibModalInstance, AlertService, paginationConstants,options) {
    	  var $ctrl=this;
    	  
    	  $ctrl.clear = clear;
    	  $ctrl.save=save;
    	  $ctrl.showLearning=false;
    	  $ctrl.account = null;
    	  $ctrl.opportunityMaster = options;
    	  $ctrl.display=display;
    	  $ctrl.updateLearning='';
    	  $ctrl.editLearning=false;
    	  $ctrl.modifiedLearn=modify;    	 
    	  
    	
    	  OpportunityLearningAIF.learningComment({id: options.id}, function(response) {          	
          	$ctrl.learnings = response;
          });
    	  LearningAIF.learningAifSubject(function(response) {          	
            	$ctrl.subjects = response;            
            });
    	  
    	  $scope.ckOptionsAdd = {
    	            language: 'en',
    	            allowedContent: true,
    	            entities: false,
    	          
    	        };
    	  $scope.ckOptionsUpdate = {
  	            language: 'en',
  	            allowedContent: true,
  	            entities: false,
  	          
  	        };
    	  $scope.onReady = function () {
              
          };
          
  $scope.clps = function () {
	  $ctrl.showLearning=true;
	 var e1= document.getElementById(closeExpanded)
	 e1.setAttribute('aria-expanded', 'false');
          };
          
    	  $scope.$on('authenticationSuccess', function() {
              getAccount();
          });
    	  
    	  getAccount();

          function getAccount() {
              Principal.identity().then(function(account) {
                  $ctrl.account = account;
                  $ctrl.isAuthenticated = Principal.isAuthenticated;
               
              });

          }
    	  function save(){        	
          	OpportunityLearningAIF.save({subject:$ctrl.subject,description:$ctrl.description,opportunityMaster:options},function(response){
          	console.log(response)
          	$ctrl.learnings.push(response);
          	$ctrl.showLearning=false;
          	$ctrl.subject='';
          	$ctrl.description='';
          	});
          }
    	  function clear () {
    		  $ctrl.learnings=[];
              $uibModalInstance.dismiss('cancel');
          }
    	  function display(learn){
    		  console.log(learn);
    		  OpportunityLearningAIF.get({id:learn.id},function(resp){
    			  $ctrl.updateLearning=resp;
    		  });
    	  }
    	  
    	  function modify(uData,index){
    		  console.log(uData);    		
    		  OpportunityLearningAIF.update(uData,function(resp){
    			  $ctrl.learnings[index].description=resp.description;
    		  });    		 
    		 
    	  }
    		
    }
})();
