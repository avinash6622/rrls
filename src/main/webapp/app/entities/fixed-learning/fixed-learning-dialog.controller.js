(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('FixedLearningController', FixedLearningController);

    FixedLearningController.$inject = ['FixedLearning', '$uibModalInstance'];

    function FixedLearningController(FixedLearning,$uibModalInstance) {
    	  var $ctrl=this;
    	  
    	  $ctrl.clear = clear;
    	  $ctrl.save=save;
    	  $ctrl.fixedLearning=[];
    	  
    	 
    	  function save(){        	
          	FixedLearning.save({subject:$ctrl.subject},function(response){
          		 $uibModalInstance.close(response);
          
          	});
          }
    	  function clear () {
    		  $ctrl.fixedLearning=[];
              $uibModalInstance.dismiss('cancel');
          }   	
    
    }
})();
