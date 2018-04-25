(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('OpportunityQuestionController', OpportunityQuestionController);

    OpportunityQuestionController.$inject = ['OpportunityQuestion','AnswerComment', 'ParseLinks', '$uibModalInstance', 'AlertService', 'paginationConstants', 'options','$filter'];

    function OpportunityQuestionController(OpportunityQuestion,AnswerComment, ParseLinks, $uibModalInstance, AlertService, paginationConstants, options,$filter) {

        var $ctrl=this;

        $ctrl.clear = clear;
        $ctrl.clearDialog = dialog;
        $ctrl.answerSubmit=submit;
        $ctrl.save=save;


        console.log(options);
        OpportunityQuestion.questionAnswer({id: options.id}, function(response) {
        	console.log(response);
        	$ctrl.questions = response;
        });
        function save(){
        	console.log('opp',options);
        	console.log('REplya',$ctrl.question);
        	OpportunityQuestion.save({questionText:$ctrl.question,opportunityMaster:options},function(response){
        	console.log(response)
        	$ctrl.questions.push(response);
        	$ctrl.question='';
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

        	AnswerComment.save({answerText : $ctrl.answerText,opportunityQuestion :id,answerComment:parentId}, function(resp) {
        		if(index !== null) {
                    if($ctrl.questions[index].answerComments !== null) {
                        $ctrl.questions[index].answerComments.push(resp);
                    } else {
                        $ctrl.questions[index].answerComments = [];
                        $ctrl.questions[index].answerComments.push(resp)
                    }
                }


        		$ctrl.answerText='';
        	console.log('answer',$ctrl.questions);
        	}, function(err) {
        		console.log(err);
        	});

        }
        function load(){
        	$ctrl.questions;
        }
    }
})();
