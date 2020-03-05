import {TestBed} from '@angular/core/testing';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from '../../view/login/login.component';
import {DependencyRootComponent} from '../dependency-root/dependency-root.component';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {ProjectService} from '../../service/project.service';
import {HeaderComponent} from '../../view/header/header.component';
import {FooterComponent} from '../../view/footer/footer.component';
import {BrowserModule} from '@angular/platform-browser';
import {
  MatButtonModule,
  MatCardModule,
  MatCheckboxModule,
  MatFormFieldModule, MatGridListModule, MatIconModule,
  MatInputModule,
  MatMenuModule, MatProgressSpinnerModule,
  MatToolbarModule
} from '@angular/material';
import {FormsModule} from '@angular/forms';

const appRoutes: Routes = [
  {path: 'login', component: LoginComponent},
  {path: 'structure-map/:projectId/:commitName', component: DependencyRootComponent},
  {path: '', redirectTo: '/dashboard', pathMatch: 'full'}
];

describe('DependencyRootComponent', () => {
  let httpTestingController: HttpTestingController;
  let service: ProjectService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [
        HeaderComponent,
        FooterComponent,
        LoginComponent,
        DependencyRootComponent
      ],
      imports: [
        BrowserModule,
        MatMenuModule,
        RouterModule.forRoot(appRoutes),
        MatToolbarModule,
        MatInputModule,
        MatCardModule,
        MatFormFieldModule,
        MatCheckboxModule,
        FormsModule,
        MatProgressSpinnerModule,
        MatButtonModule,
        MatGridListModule,
        MatIconModule,
        HttpClientTestingModule
      ],
      providers: [
        ProjectService
      ]
    });

    httpTestingController = TestBed.get(HttpTestingController);
    service = TestBed.get(ProjectService);
  });

  afterEach(() => {
    httpTestingController.verify();
  });

  // Angular default test added when you generate a service using the CLI
  it('should be created', () => {
    expect(service).toBeTruthy();
  });

  describe('getCompareTree()', () => {
    it('should return a dependencyTree comparing two commits made from test data', () => {
      const mockResponse = {
        filename: 'testSrc',
        path: '',
        packageName: '',
        level: 0,
        changed: null,
        children: [
          {
            filename: 'org',
            path: 'org',
            packageName: 'org',
            level: 0,
            changed: null,
            children: [
              {
                filename: 'wickedsource',
                path: 'org/wickedsource',
                packageName: 'org.wickedsource',
                level: 0,
                changed: null,
                children: [
                  {
                    filename: 'dependencytree',
                    path: 'org/wickedsource/dependencytree',
                    packageName: 'org.wickedsource.dependencytree',
                    level: 0,
                    changed: null,
                    children: [
                      {
                        filename: 'example',
                        path: 'org/wickedsource/dependencytree/example',
                        packageName: 'org.wickedsource.dependencytree.example',
                        level: 0,
                        changed: null,
                        children: [
                          {
                            filename: 'CoreTest.java',
                            path: 'org/wickedsource/dependencytree/example/CoreTest.java',
                            packageName: 'org.wickedsource.dependencytree.example.CoreTest.java',
                            level: 0,
                            changed: null,
                            children: [],
                            dependencies: [
                              {
                                path: 'org/wickedsource/dependencytree/example/somepackage/CoreDependencyTest.java',
                                changed: null
                              },
                              {
                                path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                                changed: null
                              },
                              {
                                path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                                changed: null
                              },
                              {
                                path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
                                changed: null
                              },
                              {
                                path: 'org/wickedsource/dependencytree/example/somepackage/FullyClassifiedDependencyTest.java',
                                changed: null
                              },
                              {
                                path: 'org/wickedsource/dependencytree/example/somepackage/NotADependencyTest.java',
                                changed: 'ADD'
                              }
                            ]
                          },
                          {
                            filename: 'somepackage',
                            path: 'org/wickedsource/dependencytree/example/somepackage',
                            packageName: 'org.wickedsource.dependencytree.example.somepackage',
                            level: 1,
                            changed: null,
                            children: [
                              {
                                filename: 'CircularDependencyTest.java',
                                path: 'org/wickedsource/dependencytree/example/somepackage/CircularDependencyTest.java',
                                packageName: 'org.wickedsource.dependencytree.example.somepackage.CircularDependencyTest.java',
                                level: 0,
                                changed: null,
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'CoreDependencyTest.java',
                                path: 'org/wickedsource/dependencytree/example/somepackage/CoreDependencyTest.java',
                                packageName: 'org.wickedsource.dependencytree.example.somepackage.CoreDependencyTest.java',
                                level: 0,
                                changed: null,
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'DuplicateDependencies2Test.java',
                                path: 'org/wickedsource/dependencytree/example/somepackage/DuplicateDependencies2Test.java',
                                packageName: 'org.wickedsource.dependencytree.example.somepackage.DuplicateDependencies2Test.java',
                                level: 0,
                                changed: null,
                                children: [],
                                dependencies: [
                                  {
                                    path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                                    changed: null
                                  },
                                  {
                                    path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                                    changed: null
                                  },
                                  {
                                    path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
                                    changed: null
                                  }
                                ]
                              },
                              {
                                filename: 'DuplicateDependenciesTest.java',
                                path: 'org/wickedsource/dependencytree/example/somepackage/DuplicateDependenciesTest.java',
                                packageName: 'org.wickedsource.dependencytree.example.somepackage.DuplicateDependenciesTest.java',
                                level: 0,
                                changed: null,
                                children: [],
                                dependencies: [
                                  {
                                    path: 'org/wickedsource/dependencytree/example/CoreTest.java',
                                    changed: null
                                  },
                                  {
                                    path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                                    changed: null
                                  },
                                  {
                                    path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                                    changed: null
                                  },
                                  {
                                    path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
                                    changed: null
                                  }
                                ]
                              },
                              {
                                filename: 'FullyClassifiedDependencyTest.java',
                                path: 'org/wickedsource/dependencytree/example/somepackage/FullyClassifiedDependencyTest.java',
                                packageName: 'org.wickedsource.dependencytree.example.somepackage.FullyClassifiedDependencyTest.java',
                                level: 0,
                                changed: null,
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'InvalidDependencyTest.java',
                                path: 'org/wickedsource/dependencytree/example/somepackage/InvalidDependencyTest.java',
                                packageName: 'org.wickedsource.dependencytree.example.somepackage.InvalidDependencyTest.java',
                                level: 0,
                                changed: null,
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'NotADependencyTest.java',
                                path: 'org/wickedsource/dependencytree/example/somepackage/NotADependencyTest.java',
                                packageName: 'org.wickedsource.dependencytree.example.somepackage.NotADependencyTest.java',
                                level: 0,
                                changed: null,
                                children: [],
                                dependencies: []
                              }
                            ],
                            dependencies: [
                              {
                                path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                                changed: null
                              },
                              {
                                path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                                changed: null
                              },
                              {
                                path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
                                changed: null
                              },
                              {
                                path: 'org/wickedsource/dependencytree/example/CoreTest.java',
                                changed: null
                              },
                              {
                                path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                                changed: null
                              },
                              {
                                path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                                changed: null
                              },
                              {
                                path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
                                changed: null
                              }
                            ]
                          },
                          {
                            filename: 'wildcardpackage',
                            path: 'org/wickedsource/dependencytree/example/wildcardpackage',
                            packageName: 'org.wickedsource.dependencytree.example.wildcardpackage',
                            level: 2,
                            changed: null,
                            children: [
                              {
                                filename: 'WildcardImport1Test.java',
                                path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                                packageName: 'org.wickedsource.dependencytree.example.wildcardpackage.WildcardImport1Test.java',
                                level: 0,
                                changed: null,
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'WildcardImport2Test.java',
                                path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                                packageName: 'org.wickedsource.dependencytree.example.wildcardpackage.WildcardImport2Test.java',
                                level: 0,
                                changed: null,
                                children: [],
                                dependencies: []
                              },
                              {
                                filename: 'WildcardImportCircularDependencyTest.java',
                                path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
                                packageName: 'org.wickedsource.dependencytree.example.wildcardpackage.WildcardImportCircularDependencyTest.java',
                                level: 0,
                                changed: null,
                                children: [],
                                dependencies: [
                                  {
                                    path: 'org/wickedsource/dependencytree/example/CoreTest.java',
                                    changed: null
                                  }
                                ]
                              }
                            ],
                            dependencies: [
                              {
                                path: 'org/wickedsource/dependencytree/example/CoreTest.java',
                                changed: null
                              }
                            ]
                          }
                        ],
                        dependencies: [
                          {
                            path: 'org/wickedsource/dependencytree/example/somepackage/CoreDependencyTest.java',
                            changed: null
                          },
                          {
                            path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                            changed: null
                          },
                          {
                            path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                            changed: null
                          },
                          {
                            path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
                            changed: null
                          },
                          {
                            path: 'org/wickedsource/dependencytree/example/somepackage/FullyClassifiedDependencyTest.java',
                            changed: null
                          },
                          {
                            path: 'org/wickedsource/dependencytree/example/somepackage/NotADependencyTest.java',
                            changed: 'ADD'
                          },
                          {
                            path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                            changed: null
                          },
                          {
                            path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                            changed: null
                          },
                          {
                            path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
                            changed: null
                          },
                          {
                            path: 'org/wickedsource/dependencytree/example/CoreTest.java',
                            changed: null
                          },
                          {
                            path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                            changed: null
                          },
                          {
                            path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                            changed: null
                          },
                          {
                            path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
                            changed: null
                          },
                          {
                            path: 'org/wickedsource/dependencytree/example/CoreTest.java',
                            changed: null
                          }
                        ]
                      }
                    ],
                    dependencies: [
                      {
                        path: 'org/wickedsource/dependencytree/example/somepackage/CoreDependencyTest.java',
                        changed: null
                      },
                      {
                        path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                        changed: null
                      },
                      {
                        path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                        changed: null
                      },
                      {
                        path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
                        changed: null
                      },
                      {
                        path: 'org/wickedsource/dependencytree/example/somepackage/FullyClassifiedDependencyTest.java',
                        changed: null
                      },
                      {
                        path: 'org/wickedsource/dependencytree/example/somepackage/NotADependencyTest.java',
                        changed: 'ADD'
                      },
                      {
                        path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                        changed: null
                      },
                      {
                        path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                        changed: null
                      },
                      {
                        path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
                        changed: null
                      },
                      {
                        path: 'org/wickedsource/dependencytree/example/CoreTest.java',
                        changed: null
                      },
                      {
                        path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                        changed: null
                      },
                      {
                        path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                        changed: null
                      },
                      {
                        path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
                        changed: null
                      },
                      {
                        path: 'org/wickedsource/dependencytree/example/CoreTest.java',
                        changed: null
                      }
                    ]
                  }
                ],
                dependencies: [
                  {
                    path: 'org/wickedsource/dependencytree/example/somepackage/CoreDependencyTest.java',
                    changed: null
                  },
                  {
                    path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                    changed: null
                  },
                  {
                    path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                    changed: null
                  },
                  {
                    path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
                    changed: null
                  },
                  {
                    path: 'org/wickedsource/dependencytree/example/somepackage/FullyClassifiedDependencyTest.java',
                    changed: null
                  },
                  {
                    path: 'org/wickedsource/dependencytree/example/somepackage/NotADependencyTest.java',
                    changed: 'ADD'
                  },
                  {
                    path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                    changed: null
                  },
                  {
                    path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                    changed: null
                  },
                  {
                    path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
                    changed: null
                  },
                  {
                    path: 'org/wickedsource/dependencytree/example/CoreTest.java',
                    changed: null
                  },
                  {
                    path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                    changed: null
                  },
                  {
                    path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                    changed: null
                  },
                  {
                    path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
                    changed: null
                  },
                  {
                    path: 'org/wickedsource/dependencytree/example/CoreTest.java',
                    changed: null
                  }
                ]
              }
            ],
            dependencies: [
              {
                path: 'org/wickedsource/dependencytree/example/somepackage/CoreDependencyTest.java',
                changed: null
              },
              {
                path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                changed: null
              },
              {
                path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                changed: null
              },
              {
                path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
                changed: null
              },
              {
                path: 'org/wickedsource/dependencytree/example/somepackage/FullyClassifiedDependencyTest.java',
                changed: null
              },
              {
                path: 'org/wickedsource/dependencytree/example/somepackage/NotADependencyTest.java',
                changed: 'ADD'
              },
              {
                path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                changed: null
              },
              {
                path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                changed: null
              },
              {
                path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
                changed: null
              },
              {
                path: 'org/wickedsource/dependencytree/example/CoreTest.java',
                changed: null
              },
              {
                path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                changed: null
              },
              {
                path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                changed: null
              },
              {
                path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
                changed: null
              },
              {
                path: 'org/wickedsource/dependencytree/example/CoreTest.java',
                changed: null
              }
            ]
          }
        ],
        dependencies: []
      };

      service.getCompareTree(1, '0b79780c8e8c8736a8e0ddafc964fc4446f007f2', '643a55c23dce1832b5da07816f068896aef854e6')
        .then(data => {
          // @ts-ignore
          expect(data.body.filename).toEqual('testSrc');
          // @ts-ignore
          const dependencyTree = data.body.children[0].children[0].children[0];
          expect(dependencyTree.children.length).toBe(4);
          expect(dependencyTree.children[0].changed).toBe('ADD');
        });
      const req = httpTestingController.
      expectOne('http://localhost:8080/analyzers/1/structureMap/0b79780c8e8c8736a8e0ddafc964fc4446f007f2/643a55c23dce1832b5da07816f068896aef854e6');
      expect(req.request.method).toEqual('GET');
      req.flush(mockResponse);
    });
  });
});
