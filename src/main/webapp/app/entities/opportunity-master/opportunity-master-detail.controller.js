(function() {
    'use strict';

    angular
        .module('researchRepositoryLearningSystemApp')
        .controller('OpportunityMasterDetailController', OpportunityMasterDetailController);

    OpportunityMasterDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'OpportunityMaster', 'StrategyMaster', 'Upload', 'FileUploadComments','FileUpload'];

    function OpportunityMasterDetailController($scope, $rootScope, $stateParams, previousState, entity, OpportunityMaster, StrategyMaster, Upload, FileUploadComments,FileUpload) {
        var vm = this;

        vm.opportunityMaster = entity;
        vm.previousState = previousState.name;
        vm.readOnly = true;
        vm.upload = upload;
        vm.selectFile = selectFile;
        vm.saveComment = saveComment;
        vm.load = load;
        vm.loadFileContent = loadFileContent; 
        vm.fileId='';
        vm.createFile = createFile;
        vm.approveFile=approveFile;
        vm.aStatus='';
              
       
        var unsubscribe = $rootScope.$on('researchRepositoryLearningSystemApp:opportunityMasterUpdate', function(event, result) {
            vm.opportunityMaster = result;
        });
        $scope.$on('$destroy', unsubscribe);

        // Editor options.
        $scope.options = {
            language: 'en',
            allowedContent: true,
            entities: false
        };

        // Called when the editor is completely ready.
        $scope.onReady = function () {
            vm.readOnly = false;
        };
        
     // $scope.myVar = false;
        $scope.showdocument = function()
        {
            $scope.myvar = !$scope.myvar;

        };

        function load() {

            vm.opportunityMaster.fileUploadCommentList = OpportunityMaster.get({id : $stateParams.id}, function(resp){
             console.log(resp);
            vm.opportunityMaster.fileUploadCommentList = resp.fileUploadCommentList;
               }, function(err) {
                console.log(err);
            });
        } 
        
        function clear () {
        	vm.newdoc = "";
        	$scope.myvar = false;
        	vm.fileName="";
        }

        function upload () {
        console.log('uploading....');
            vm.isSaving = true;

                //OpportunityMaster.saveWithUpload({inputData: vm.opportunityMaster, fileUpload: vm.opportunityMaster.fileUpload}, onSaveSuccess, onSaveError);
                Upload.upload({
                    url: 'api/file-uploads',
                    data: {fileUploads: vm.opportunityMaster.fileUpload},
                    params: {oppId: vm.opportunityMaster.id }// {oppCode: inputData.oppCode, oppName: inputData.oppName, oppDescription: inputData.oppDescription, strategyMasterId: inputData.strategyMasterId.id}
                }).then(function (resp) {
                    console.log(resp);
                }, function (resp) {
                    console.log(resp);
                }, function (evt) {
                    console.log(evt);
                    //var progressPercentage = parseInt(100.0 * evt.loaded / evt.total);
                    //console.log('progress: ' + progressPercentage + '% ' + evt.config.data.file.name);
                });
        }
        function selectFile (file) {
            vm.opportunityMaster.fileUpload = file;
        }
        
        function createFile()
        {
        	console.log(vm.newdoc);
        	console.log('File name......'+vm.fileName);
        	var doc = "<body>"+vm.newdoc+"</body>";
        	OpportunityMaster.wordCreation({fileContent: doc,fileName:vm.fileName,oppId:vm.opportunityMaster.id,
        		oppName:vm.opportunityMaster.masterName.oppName,oppId:vm.opportunityMaster.id}, function(result){
        			console.log('result',result);
        			vm.opportunityMaster.fileUploads.push(result);
        			console.log('opp',vm.opportunityMaster.fileUploads);
        			clear();
        		}, onSaveError);
       
       
        }

        function saveComment() {
            vm.isSaving = true;
            var inputData = {};            
            angular.forEach(vm.opportunityMaster.fileUploads, function(oppKey, oppValue){            
            	if(vm.fileId == vm.opportunityMaster.fileUploads[oppValue].id){
            		inputData.fileUploadId = vm.opportunityMaster.fileUploads[oppValue];
            	}
            });
            
            //inputData.fileUploadId = vm.fileId;
            inputData.fileComments = vm.fileComments;
            FileUploadComments.save(inputData, onSaveSuccess, onSaveError);
        }
      /*  $scope.data='';*/
       function approveFile(numeric){
        	console.log(vm.fileId);         	
        	console.log(numeric);
        	var status = "";
        	if(numeric == 1 ){        		
        		status = "APP";
        	}
        	
        	if(numeric == 2 ){        	
        		status = "CRR";
        	}
        	if(numeric == 3 ){
        		console.log("RE");
        		status = "RE";
        	}
        	console.log(status +'datas');
        	FileUpload.get({id : vm.fileId}, function(resp)
        			{        		
        		console.log(resp.opportunityMasterId);
        		console.log(resp.fileStatus);
        		resp.fileStatus=status;
        		FileUpload.update(resp, onSaveSuccess, onSaveError)
        			});      	
        }
        
        function loadFileContent(fileID) {
        	console.log(fileID);
        	vm.fileId=fileID;
        	OpportunityMaster.getFileContent({id : fileID}, function(resp){
                console.log(resp);     
                console.log("my variable.. "+$scope.myvar);
                vm.htmlContent = resp.htmlContent;
                vm.fileUploadCommentList = resp.fileUploadCommentList;
                $scope.myvar = false;
                  }, function(err) {
                   console.log(err);
               });
        }       
      

        function onSaveSuccess (result) {
            vm.isSaving = false;
            vm.fileUploadCommentList.push(result);           
            vm.fileComments = "";
            //vm.load();
        }

        function onSaveError () {
            vm.isSaving = false;
        }
    }
})();
