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
        	if(index1==null)
        		var name='Answered';
        	else
        		var name='Replied';
        	console.log(name);
        		AnswerComment.save({answerText : $ctrl.answerText,opportunityQuestion :id,answerComment:parentId,commentStatus:name}, function(resp) {
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
