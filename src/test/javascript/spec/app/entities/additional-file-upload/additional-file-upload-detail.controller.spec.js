'use strict';

describe('Controller Tests', function() {

    describe('AdditionalFileUpload Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockAdditionalFileUpload, MockFileUpload;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockAdditionalFileUpload = jasmine.createSpy('MockAdditionalFileUpload');
            MockFileUpload = jasmine.createSpy('MockFileUpload');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'AdditionalFileUpload': MockAdditionalFileUpload,
                'FileUpload': MockFileUpload
            };
            createController = function() {
                $injector.get('$controller')("AdditionalFileUploadDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'researchRepositoryLearningSystemApp:additionalFileUploadUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
