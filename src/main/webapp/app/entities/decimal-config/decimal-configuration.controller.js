(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('DecimalConfigurationController', DecimalConfigurationController);

    DecimalConfigurationController.$inject = ['DecimalConfiguration','Principal','entity','$scope','ParseLinks', 'AlertService'];

    function DecimalConfigurationController(DecimalConfiguration,Principal,entity,$scope, ParseLinks, AlertService) {
    	  var vm=this;
    	  vm.decimalConfiguration=entity;
    	  vm.account='';
    	  
    	  vm.save=save;
    	  
    	  $scope.$on('authenticationSuccess', function() {
              getAccount();
          });
    	  
    	  getAccount();

          function getAccount() {
              Principal.identity().then(function(account) {
                  vm.decimalConfiguration.user = account;
                  vm.isAuthenticated = Principal.isAuthenticated;
               console.log('Account',account)
              });

          }

    
    	/* $scope.decimalSlider = 4;*/
    	 vm.myChangeListener = function(sliderId) {
    		    console.log(sliderId, 'has changed with ', vm.slider.value);
    		    vm.decimalConfiguration.decimalValue = vm.slider.value;
    		  };
    	 vm.slider = {
    			  value:4,
    			  options: {
    				  showTicksValues: true,
    				  id: 'sliderA',
    			      onChange: vm.myChangeListener
    				 
    			  }
    	  };
    	function save(){
    		 console.log('Rupee',vm.decimalConfiguration.rupee);
    		  console.log('has changed ', vm.decimalConfiguration.decimalValue);
    		  console.log('User',vm.decimalConfiguration.user)
    		  DecimalConfiguration.save(vm.decimalConfiguration)
    	}
    	
    	
    	/*console.log('decimalSlider',decimalSlider);*/
    }
})();
