(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('DecimalConfigurationController', DecimalConfigurationController);

    DecimalConfigurationController.$inject = ['DecimalConfiguration','Principal','entity','$scope','ParseLinks', 'AlertService','$filter'];

    function DecimalConfigurationController(DecimalConfiguration,Principal,entity,$scope, ParseLinks, AlertService,$filter) {
    	  var vm=this;
    	  vm.decimalConfiguration=entity;
    	  vm.account='';

    	  vm.save=save;

    	  $scope.$on('authenticationSuccess', function() {

              getAccount();
              getDescription();
          });


        var myDate=new Date();

        $scope.currentYear = $filter('date')(myDate,'yyyy');

    	  getAccount();
    	  getDescription();
    	  function getDescription(){
    		  console.log('decimal',vm.decimalConfiguration.rupee);
    		  if(vm.decimalConfiguration.rupee==''){
    		  vm.decimalConfiguration.decimalValue=2;
    		  vm.decimalConfiguration.rupee='Crores';}
    	  }

          function getAccount() {
              Principal.identity().then(function(account) {
                  vm.decimalConfiguration.user = account;
                  vm.isAuthenticated = Principal.isAuthenticated;
               console.log('Account',account)
              });

          }


    	/* $scope.decimalSlider = 4;*/
    	 vm.myChangeListener = function(sliderId) {
    		    console.log(sliderId, 'has changed with ', vm.decimalConfiguration.decimalValue);
    		   /* vm.decimalConfiguration.decimalValue = vm.slider.value;*/
    		  };
    	 vm.slider = {

    			  options: {
    				  showTicksValues: true,
    				  id: 'sliderA',
    				    floor: 0,
    		            ceil: 4,
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
