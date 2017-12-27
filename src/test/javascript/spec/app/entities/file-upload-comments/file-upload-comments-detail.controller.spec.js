'use strict';

describe('Controller Tests', function() {

    describe('FileUploadComments Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockFileUploadComments, MockFileUpload, MockAdditionalFileUpload;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockFileUploadComments = jasmine.createSpy('MockFileUploadComments');
            MockFileUpload = jasmine.createSpy('MockFileUpload');
            MockAdditionalFileUpload = jasmine.createSpy('MockAdditionalFileUpload');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'FileUploadComments': MockFileUploadComments,
                'FileUpload': MockFileUpload,
                'AdditionalFileUpload': MockAdditionalFileUpload
            };
            createController = function() {
                $injector.get('$controller')("FileUploadCommentsDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'researchRepositoryLearningSystemApp:fileUploadCommentsUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
