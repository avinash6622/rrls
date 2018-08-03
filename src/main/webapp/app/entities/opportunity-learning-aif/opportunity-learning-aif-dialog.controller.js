(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('LearningAIFDialogController', LearningAIFDialogController);

    LearningAIFDialogController.$inject = ['LearningAIF', '$uibModalInstance'];

    function LearningAIFDialogController(LearningAIF,$uibModalInstance) {
    	  var $ctrl=this;
    	  
    	  $ctrl.clear = clear;
    	  $ctrl.save=save;
    	  $ctrl.learningAIF=[];
    	  
    	 
    	  function save(){        	
    		  LearningAIF.save({subject:$ctrl.subject},function(response){
          		 $uibModalInstance.close(response);
          
          	});
          }
    	  function clear () {
    		  $ctrl.learningAIF=[];
              $uibModalInstance.dismiss('cancel');
          }   	
    
    }
})();
