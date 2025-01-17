(function() {
  "use strict";

  angular.module("researchRepositoryLearningSystemApp").config(stateConfig);

  stateConfig.$inject = ["$stateProvider"];

  function stateConfig($stateProvider) {
    $stateProvider
      .state("presentation-master", {
        parent: "entity",
        url: "/presentation-master",
        data: {
          authorities: ["User"],
          pageTitle: "Presentation Master"
        },
        views: {
          "content@": {
            templateUrl:
              "app/entities/presentation-fileupload/presentation-master.html",
            controller: "PresentationMasterController",
            controllerAs: "vm"
          }
        },

        params: {
          page: {
            value: "1",
            squash: true
          },
          sort: {
            value: "id,asc",
            squash: true
          }
        },
        resolve: {
          pagingParams: [
            "$stateParams",
            "PaginationUtil",
            function($stateParams, PaginationUtil) {
              return {
                page: PaginationUtil.parsePage($stateParams.page),
                sort: $stateParams.sort,
                predicate: PaginationUtil.parsePredicate($stateParams.sort),
                ascending: PaginationUtil.parseAscending($stateParams.sort)
              };
            }
          ]
        }
      })
      .state("presentation-master-detail", {
        parent: "presentation-master",
        url: "/presentation-master/{id}",
        data: {
          authorities: ["User"],
          pageTitle: "Presentation Master"
        },
        views: {
          "content@": {
            templateUrl:
              "app/entities/presentation-fileupload/presentation-master-detail.html",
            controller: "PresentationMasterDetailController",
            controllerAs: "vm"
          }
        },

        params: {
          page: {
            value: "1",
            squash: true
          },
          sort: {
            value: "id,asc",
            squash: true
          }
        },
        resolve: {
          /* entity: ['$stateParams', 'StrategyMaster', function($stateParams, StrategyMaster) {
                    return StrategyMaster.get({id : $stateParams.id}).$promise;
                }],*/
          pagingParams: [
            "$stateParams",
            "PaginationUtil",
            function($stateParams, PaginationUtil) {
              return {
                page: PaginationUtil.parsePage($stateParams.page),
                sort: $stateParams.sort,
                predicate: PaginationUtil.parsePredicate($stateParams.sort),
                ascending: PaginationUtil.parseAscending($stateParams.sort)
              };
            }
          ],
          previousState: [
            "$state",
            function($state) {
              var currentStateData = {
                name: $state.current.name || "presentation-master",
                params: $state.params,
                url: $state.href($state.current.name, $state.params)
              };
              return currentStateData;
            }
          ]
        }
      })
      .state("presentation-master-detail.presentation-new", {
        parent: "presentation-master-detail",
        url: "/presentation-new",
        data: {
          authorities: ["User"],
          pageTitle: "Presentation Master"
        },
        views: {
          "content@": {
            templateUrl:
              "app/entities/presentation-fileupload/presentation-master-upload.html",
            controller: "PresentationMasterUploadController",
            controllerAs: "vm"
          }
        },

        params: {
          page: {
            value: "1",
            squash: true
          },
          sort: {
            value: "id,asc",
            squash: true
          }
        },
        resolve: {
          /* entity: ['$stateParams', 'StrategyMaster', function($stateParams, StrategyMaster) {
                    return StrategyMaster.get({id : $stateParams.id}).$promise;
                }],*/
          pagingParams: [
            "$stateParams",
            "PaginationUtil",
            function($stateParams, PaginationUtil) {
              return {
                page: PaginationUtil.parsePage($stateParams.page),
                sort: $stateParams.sort,
                predicate: PaginationUtil.parsePredicate($stateParams.sort),
                ascending: PaginationUtil.parseAscending($stateParams.sort)
              };
            }
          ],
          previousState: [
            "$state",
            function($state) {
              var currentStateData = {
                name: $state.current.name || "presentation-master",
                params: $state.params,
                url: $state.href($state.current.name, $state.params)
              };
              return currentStateData;
            }
          ]
        }
      })
      .state("presentation-master-detail.brochure-new", {
        parent: "presentation-master-detail",
        url: "/brochure-new",
        data: {
          authorities: ["User"],
          pageTitle: "Presentation Master"
        },
        views: {
          "content@": {
            templateUrl:
              "app/entities/presentation-fileupload/brochure-master-upload.html",
            controller: "PresentationBrochureMasterUploadController",
            controllerAs: "vm"
          }
        },

        params: {
          page: {
            value: "1",
            squash: true
          },
          sort: {
            value: "id,asc",
            squash: true
          }
        },
        resolve: {
          /* entity: ['$stateParams', 'StrategyMaster', function($stateParams, StrategyMaster) {
                    return StrategyMaster.get({id : $stateParams.id}).$promise;
                }],*/
          pagingParams: [
            "$stateParams",
            "PaginationUtil",
            function($stateParams, PaginationUtil) {
              return {
                page: PaginationUtil.parsePage($stateParams.page),
                sort: $stateParams.sort,
                predicate: PaginationUtil.parsePredicate($stateParams.sort),
                ascending: PaginationUtil.parseAscending($stateParams.sort)
              };
            }
          ],
          previousState: [
            "$state",
            function($state) {
              var currentStateData = {
                name: $state.current.name || "presentation-master",
                params: $state.params,
                url: $state.href($state.current.name, $state.params)
              };
              return currentStateData;
            }
          ]
        }
      })
      .state("presentation-master-detail.brochuresupportfile-new", {
        parent: "presentation-master-detail",
        url: "/brochure-supportfile-new/{bId}/create",
        data: {
          authorities: ["User"]
        },
        views: {
          "content@": {
            templateUrl:
              "app/entities/presentation-fileupload/brochure-master-supportingfile-upload.html",
            controller: "PresentationBrochureMasterSupportFileUploadController",
            controllerAs: "vm"
          }
        },

        params: {
          page: {
            value: "1",
            squash: true
          },
          sort: {
            value: "id,asc",
            squash: true
          }
        },
        resolve: {
          /* entity: ['$stateParams', 'StrategyMaster', function($stateParams, StrategyMaster) {
                    return StrategyMaster.get({id : $stateParams.id}).$promise;
                }],*/
          pagingParams: [
            "$stateParams",
            "PaginationUtil",
            function($stateParams, PaginationUtil) {
              return {
                page: PaginationUtil.parsePage($stateParams.page),
                sort: $stateParams.sort,
                predicate: PaginationUtil.parsePredicate($stateParams.sort),
                ascending: PaginationUtil.parseAscending($stateParams.sort)
              };
            }
          ],
          previousState: [
            "$state",
            function($state) {
              var currentStateData = {
                name: $state.current.name || "presentation-master",
                params: $state.params,
                url: $state.href($state.current.name, $state.params)
              };
              return currentStateData;
            }
          ]
        }
      })
      // .state("presentation-master.brochuresupportfile-new", {
      //   parent: "presentation-master",
      //   url: "/brochure-supportfile-new/{bid}/create",
      //   data: {
      //     authorities: ["User"],
      //     pageTitle: "Presentation Master"
      //   },
      //   views: {
      //     "content@": {
      //       templateUrl:
      //         "app/entities/presentation-fileupload/brochure-master-supportingfile-upload.html",
      //       controller: "PresentationBrochureMasterSupportFileUploadController",
      //       controllerAs: "vm"
      //     }
      //   },

      //   params: {
      //     page: {
      //       value: "1",
      //       squash: true
      //     },
      //     sort: {
      //       value: "id,asc",
      //       squash: true
      //     }
      //   },
      //   resolve: {
      //     /* entity: ['$stateParams', 'StrategyMaster', function($stateParams, StrategyMaster) {
      //               return StrategyMaster.get({id : $stateParams.id}).$promise;
      //           }],*/
      //     pagingParams: [
      //       "$stateParams",
      //       "PaginationUtil",
      //       function($stateParams, PaginationUtil) {
      //         return {
      //           page: PaginationUtil.parsePage($stateParams.page),
      //           sort: $stateParams.sort,
      //           predicate: PaginationUtil.parsePredicate($stateParams.sort),
      //           ascending: PaginationUtil.parseAscending($stateParams.sort)
      //         };
      //       }
      //     ],
      //     previousState: [
      //       "$state",
      //       function($state) {
      //         var currentStateData = {
      //           name: $state.current.name || "presentation-master",
      //           params: $state.params,
      //           url: $state.href($state.current.name, $state.params)
      //         };
      //         return currentStateData;
      //       }
      //     ]
      //   }
      // })
      // .state("presentation-master.edit", {
      //   parent: "presentation-master",
      //   url: "/{id}/edit",
      //   data: {
      //     authorities: ["User"]
      //   },
      //   onEnter: [
      //     "$stateParams",
      //     "$state",
      //     "$uibModal",
      //     function($stateParams, $state, $uibModal) {
      //       $uibModal
      //         .open({
      //           templateUrl:
      //             "app/entities/presentation-fileupload/presentation-master-dialog.html",
      //           controller: "PresentationMasterDialogController",
      //           controllerAs: "vm",
      //           backdrop: "static",
      //           size: "lg",
      //           resolve: {
      //             entity: [
      //               "PresentationMaster",
      //               function(PresentationMaster) {
      //                 return PresentationMaster.get({ id: $stateParams.id })
      //                   .$promise;
      //               }
      //             ]
      //           }
      //         })
      //         .result.then(
      //           function() {
      //             $state.go("presentation-master", null, {
      //               reload: "presentation-master"
      //             });
      //           },
      //           function() {
      //             $state.go("^");
      //           }
      //         );
      //     }
      //   ]
      // })
      .state("presentation-master-detail.delete", {
        parent: "presentation-master-detail",
        url: "/{id}/delete",
        data: {
          authorities: ["User"]
        },
        onEnter: [
          "$stateParams",
          "$state",
          "$uibModal",
          function($stateParams, $state, $uibModal) {
            $uibModal
              .open({
                templateUrl:
                  "app/entities/presentation-fileupload/presentation-master-delete-dialog.html",
                controller: "PresentationMasterDeleteController",
                controllerAs: "vm",
                size: "md",
                resolve: {
                  entity: [
                    "PresentationMaster",
                    function(PresentationMaster) {
                      return PresentationMaster.get({ id: $stateParams.id })
                        .$promise;
                    }
                  ]
                }
              })
              .result.then(
                function() {
                  $state.go("presentation-master", null, {
                    reload: "presentation-master"
                  });
                },
                function() {
                  $state.go("^");
                }
              );
          }
        ]
      })
      .state("presentation-master-detail.presentation-edit", {
        parent: "presentation-master-detail",
        url: "/presentation/{pId}/edit",
        data: {
          authorities: ["User"]
        },
        views: {
          "content@": {
            templateUrl:
              "app/entities/presentation-fileupload/presentation-master-dialog.html",
            controller: "PresentationMasterDialogController",
            controllerAs: "vm"
          }
        },

        params: {
          page: {
            value: "1",
            squash: true
          },
          sort: {
            value: "id,asc",
            squash: true
          }
        },
        resolve: {
          /* entity: ['$stateParams', 'StrategyMaster', function($stateParams, StrategyMaster) {
                    return StrategyMaster.get({id : $stateParams.id}).$promise;
                }],*/
          pagingParams: [
            "$stateParams",
            "PaginationUtil",
            function($stateParams, PaginationUtil) {
              return {
                page: PaginationUtil.parsePage($stateParams.page),
                sort: $stateParams.sort,
                predicate: PaginationUtil.parsePredicate($stateParams.sort),
                ascending: PaginationUtil.parseAscending($stateParams.sort)
              };
            }
          ],
          previousState: [
            "$state",
            function($state) {
              var currentStateData = {
                name: $state.current.name || "presentation-master",
                params: $state.params,
                url: $state.href($state.current.name, $state.params)
              };
              return currentStateData;
            }
          ]
        }
      })
      .state("presentation-master-detail.brochure-edit", {
        parent: "presentation-master-detail",
        url: "/brochure/{bId}/edit",
        data: {
          authorities: ["User"]
        },
        views: {
          "content@": {
            templateUrl:
              "app/entities/presentation-fileupload/brochure-master-dialog.html",
            controller: "PresentationBrochureMasterDialogController",
            controllerAs: "vm"
          }
        },

        params: {
          page: {
            value: "1",
            squash: true
          },
          sort: {
            value: "id,asc",
            squash: true
          }
        },
        resolve: {
          /* entity: ['$stateParams', 'StrategyMaster', function($stateParams, StrategyMaster) {
                    return StrategyMaster.get({id : $stateParams.id}).$promise;
                }],*/
          pagingParams: [
            "$stateParams",
            "PaginationUtil",
            function($stateParams, PaginationUtil) {
              return {
                page: PaginationUtil.parsePage($stateParams.page),
                sort: $stateParams.sort,
                predicate: PaginationUtil.parsePredicate($stateParams.sort),
                ascending: PaginationUtil.parseAscending($stateParams.sort)
              };
            }
          ],
          previousState: [
            "$state",
            function($state) {
              var currentStateData = {
                name: $state.current.name || "presentation-master",
                params: $state.params,
                url: $state.href($state.current.name, $state.params)
              };
              return currentStateData;
            }
          ]
        }
      })
      .state("presentation-master-detail.brochure-supportfile-edit", {
        parent: "presentation-master-detail",
        url: "/brochuresupportfile/{bId}/edit/{bSID}/data",
        data: {
          authorities: ["User"]
        },
        views: {
          "content@": {
            templateUrl:
              "app/entities/presentation-fileupload/brochure-master-supportfileupdate-dialog.html",
            controller: "PresentationBrochureSupportFileUpdateDialogController",
            controllerAs: "vm"
          }
        },

        params: {
          page: {
            value: "1",
            squash: true
          },
          sort: {
            value: "id,asc",
            squash: true
          }
        },
        resolve: {
          /* entity: ['$stateParams', 'StrategyMaster', function($stateParams, StrategyMaster) {
                    return StrategyMaster.get({id : $stateParams.id}).$promise;
                }],*/
          pagingParams: [
            "$stateParams",
            "PaginationUtil",
            function($stateParams, PaginationUtil) {
              return {
                page: PaginationUtil.parsePage($stateParams.page),
                sort: $stateParams.sort,
                predicate: PaginationUtil.parsePredicate($stateParams.sort),
                ascending: PaginationUtil.parseAscending($stateParams.sort)
              };
            }
          ],
          previousState: [
            "$state",
            function($state) {
              var currentStateData = {
                name: $state.current.name || "presentation-master",
                params: $state.params,
                url: $state.href($state.current.name, $state.params)
              };
              return currentStateData;
            }
          ]
        }
      });
  }
})();
