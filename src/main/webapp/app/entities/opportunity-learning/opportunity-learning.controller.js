(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('OpportunityLearningController', OpportunityLearningController);

    OpportunityLearningController.$inject = ['OpportunityLearning', '$scope','ParseLinks', '$uibModalInstance', 'AlertService', 'paginationConstants','options'];

    function OpportunityLearningController(OpportunityLearning,$scope, ParseLinks, $uibModalInstance, AlertService, paginationConstants,options) {
    	  var $ctrl=this;
    	  
    	  $ctrl.clear = clear;
    	  $ctrl.save=save;
    	  $ctrl.showLearning=false;
    	  
    	  console.log(options);
    	  OpportunityLearning.learningComment({id: options.id}, function(response) {
          	console.log(response);
          	$ctrl.learnings = response;
          });
    	  
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
          	
              $uibModalInstance.dismiss('cancel');
          }
    }
})();
