(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('OpportunityCommentController', OpportunityCommentController);

    OpportunityCommentController.$inject = ['CommentOpportunity','ReplyComment', 'ParseLinks', '$uibModalInstance', 'AlertService', 'paginationConstants', 'options','$filter'];

    function OpportunityCommentController(CommentOpportunity,ReplyComment,ParseLinks, $uibModalInstance, AlertService, paginationConstants, options,$filter) {

        var $ctrl=this;
        $ctrl.clear = clear;
        $ctrl.clearDialog = dialog;
        $ctrl.answerSubmit=submit;
        $ctrl.save=save;


        console.log(options);
        CommentOpportunity.comment({id: options.id}, function(response) {
            console.log(response);
            $ctrl.comments = response;
        });
        function save(){
            console.log('opp',options);
            console.log('REplya',$ctrl.comment);
            CommentOpportunity.save({commentText:$ctrl.comment,opportunityMaster:options},function(response){
                console.log(response)
                $ctrl.comments.push(response);
                $ctrl.comment='';
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
            console.log(index1);

            ReplyComment.save({replyText : $ctrl.answerText,commentOpportunity :id,replyComment:parentId}, function(resp) {
                console.log(resp);
                if(index !== null) {
                    if($ctrl.comments[index].commentList !== null) {
                        $ctrl.comments[index].commentList.push(resp);
                    } else {
                        $ctrl.comments[index].commentList = [];
                        $ctrl.comments[index].commentList.push(resp)
                    }
                }


                $ctrl.answerText='';
                console.log('answer',$ctrl.comments);
            }, function(err) {
                console.log(err);
            });

        }
        function load(){
            $ctrl.comments;
        }
    }
})();
