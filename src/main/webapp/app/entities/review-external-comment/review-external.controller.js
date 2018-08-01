(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('ReviewExternalController', ReviewExternalController);

    ReviewExternalController.$inject = ['ReviewExternal', 'ParseLinks', '$uibModalInstance', 'AlertService', 'paginationConstants', 'options','$filter','$scope'];

    function ReviewExternalController(ReviewExternal,ParseLinks, $uibModalInstance, AlertService, paginationConstants, options,$filter,$scope) {

        var $ctrl=this;
        $ctrl.clear = clear;
        $ctrl.clearDialog = dialog;       
        $ctrl.save=save;
        $ctrl.display=display;
        $ctrl.updateReview='';
        $ctrl.update=update;


        console.log(options);
        ReviewExternal.reviews({id: options.id}, function(response) {
            console.log(response);
            $ctrl.reviews = response;
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
        
        function display(review){
  		  console.log(review);
  		ReviewExternal.get({id:review.id},function(resp){
  			  $ctrl.updateReview=resp;
  		  });
  	  }
        function save(){
            
            ReviewExternal.save({reviewText:$ctrl.comment,externalResearchAnalyst:options},function(response){
                console.log(response)
                $ctrl.reviews.push(response);
                $ctrl.comment='';
                $ctrl.showLearning=false;
            });
        }
    function update(updateReview,index){
            console.log(index);
            ReviewExternal.update(updateReview,function(response){               
                $ctrl.reviews[index].reviewText=response.reviewText;              
            });
        }
        function dialog(){
            $uibModalInstance.dismiss('cancel');
        }
        function clear () {        	
            $ctrl.answerText.clear();
            $uibModalInstance.dismiss('cancel');
        }
     
        function load(){
            $ctrl.reviews;
        }
    }
})();
