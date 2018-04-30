(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('OpportunityLearningController', OpportunityLearningController);

    OpportunityLearningController.$inject = ['OpportunityLearning', '$scope','Principal','ParseLinks', '$uibModalInstance', 'AlertService', 'paginationConstants','options'];

    function OpportunityLearningController(OpportunityLearning,$scope, Principal,ParseLinks, $uibModalInstance, AlertService, paginationConstants,options) {
    	  var $ctrl=this;
    	  
    	  $ctrl.clear = clear;
    	  $ctrl.save=save;
    	  $ctrl.showLearning=false;
    	  $ctrl.account = null;
    	  $ctrl.opportunityMaster = options;
    	  
    	  console.log(options);
    	  OpportunityLearning.learningComment({id: options.id}, function(response) {
          	console.log(response);
          	$ctrl.learnings = response;
          });
    	  $scope.ckOptions = {
    	            language: 'en',
    	            allowedContent: true,
    	            entities: false,
    	          
    	        };
    	  $scope.$on('authenticationSuccess', function() {
              getAccount();
          });
    	  
    	  getAccount();

          function getAccount() {
              Principal.identity().then(function(account) {
                  $ctrl.account = account;
                  $ctrl.isAuthenticated = Principal.isAuthenticated;
                  console.log('Acoount name', $ctrl.account);
              });

          }
    	  function save(){        	
          	OpportunityLearning.save({subject:$ctrl.subject,description:$ctrl.description,opportunityMaster:options},function(response){
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
    }
})();