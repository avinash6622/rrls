(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('ReviewExternalController', ReviewExternalController);

    ReviewExternalController.$inject = ['ReviewExternal','ReplyReview', 'ParseLinks', '$uibModalInstance', 'AlertService', 'paginationConstants', 'options','$filter','$scope'];

    function ReviewExternalController(ReviewExternal,ReplyReview,ParseLinks, $uibModalInstance, AlertService, paginationConstants, options,$filter,$scope) {

        var $ctrl=this;
        $ctrl.clear = clear;
        $ctrl.clearDialog = dialog;
        $ctrl.answerSubmit=submit;
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
        function submit(id, parentId, index,index1){
            console.log(index);
            console.log(parentId);
            var name='Replied';
            console.log('skdjk',id);

            ReplyReview.save({replyText : $ctrl.answerText, reviewExternal :id, replyReview :parentId,commentStatuscol:name}, function(resp) {
                console.log(resp);
               if(index !== null) {
                    if($ctrl.reviews[index].reviewExternalList !== null) {
                        $ctrl.reviews[index].reviewExternalList.push(resp);
                    } else {
                        $ctrl.reviews[index].reviewExternalList = [];
                        $ctrl.reviews[index].reviewExternalList.push(resp)
                    }
                }


                $ctrl.answerText='';
                console.log('answer',$ctrl.reviews);
            }, function(err) {
                console.log(err);
            });

        }
        function load(){
            $ctrl.reviews;
        }
    }
})();
