'use strict';

describe('Controller Tests', function() {

    describe('FileUpload Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockFileUpload, MockOpportunityMaster;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockFileUpload = jasmine.createSpy('MockFileUpload');
            MockOpportunityMaster = jasmine.createSpy('MockOpportunityMaster');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'FileUpload': MockFileUpload,
                'OpportunityMaster': MockOpportunityMaster
            };
            createController = function() {
                $injector.get('$controller')("FileUploadDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'researchRepositoryLearningSystemApp:fileUploadUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
