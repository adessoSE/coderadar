import {TestBed} from '@angular/core/testing';
import {DependencyRootComponent} from './dependency-root.component';
import {BrowserModule} from '@angular/platform-browser';
import {HttpClientTestingModule, HttpTestingController} from '@angular/common/http/testing';
import {ProjectService} from '../../service/project.service';
import {HeaderComponent} from '../../view/header/header.component';
import {
  MatButtonModule,
  MatIconModule,
  MatInputModule,
  MatMenuModule,
  MatProgressSpinnerModule,
  MatToolbarModule,
  MatCheckboxModule,
  MatCardModule,
  MatFormFieldModule,
  MatGridListModule
} from '@angular/material';
import {RouterModule, Routes} from '@angular/router';
import {LoginComponent} from '../../view/login/login.component';
import {FormsModule} from '@angular/forms';
import {FooterComponent} from '../../view/footer/footer.component';

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

  describe('getDependencyTree()', () => {
    it('should return a dependencyTree made from test data', () => {
      const mockResponse = {
        filename: 'testSrc',
        path: '',
        packageName: '',
        level: -1,
        children: [
        {
          filename: 'org',
          path: 'org',
          packageName: 'org',
          level: 0,
          children: [
            {
              filename: 'wickedsource',
              path: 'org/wickedsource',
              packageName: 'org.wickedsource',
              level: 0,
              children: [
                {
                  filename: 'dependencytree',
                  path: 'org/wickedsource/dependencytree',
                  packageName: 'org.wickedsource.dependencytree',
                  level: 0,
                  children: [
                    {
                      filename: 'example',
                      path: 'org/wickedsource/dependencytree/example',
                      packageName: 'org.wickedsource.dependencytree.example',
                      level: 0,
                      children: [
                        {
                          filename: 'CoreTest.java',
                          path: 'org/wickedsource/dependencytree/example/CoreTest.java',
                          packageName: 'org.wickedsource.dependencytree.example.CoreTest.java',
                          level: 0,
                          children: [],
                          dependencies: [
                            'org/wickedsource/dependencytree/example/somepackage/CoreDependencyTest.java',
                            'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                            'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                            'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
                            'org/wickedsource/dependencytree/example/somepackage/NotADependencyTest.java',
                            'org/wickedsource/dependencytree/example/somepackage/FullyClassifiedDependencyTest.java'
                          ]
                        },
                        {
                          filename: 'somepackage',
                          path: 'org/wickedsource/dependencytree/example/somepackage',
                          packageName: 'org.wickedsource.dependencytree.example.somepackage',
                          level: 1,
                          children: [
                            {
                              filename: 'CircularDependencyTest.java',
                              path: 'org/wickedsource/dependencytree/example/somepackage/CircularDependencyTest.java',
                              packageName: 'org.wickedsource.dependencytree.example.somepackage.CircularDependencyTest.java',
                              level: 0,
                              children: [],
                              dependencies: []
                            },
                            {
                              filename: 'CoreDependencyTest.java',
                              path: 'org/wickedsource/dependencytree/example/somepackage/CoreDependencyTest.java',
                              packageName: 'org.wickedsource.dependencytree.example.somepackage.CoreDependencyTest.java',
                              level: 0,
                              children: [],
                              dependencies: []
                            },
                            {
                              filename: 'DuplicateDependencies2Test.java',
                              path: 'org/wickedsource/dependencytree/example/somepackage/DuplicateDependencies2Test.java',
                              packageName: 'org.wickedsource.dependencytree.example.somepackage.DuplicateDependencies2Test.java',
                              level: 0,
                              children: [],
                              dependencies: [
                                'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                                'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                                'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java'
                              ]
                            },
                            {
                              filename: 'DuplicateDependenciesTest.java',
                              path: 'org/wickedsource/dependencytree/example/somepackage/DuplicateDependenciesTest.java',
                              packageName: 'org.wickedsource.dependencytree.example.somepackage.DuplicateDependenciesTest.java',
                              level: 0,
                              children: [],
                              dependencies: [
                                'org/wickedsource/dependencytree/example/CoreTest.java',
                                'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                                'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                                'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java'
                              ]
                            },
                            {
                              filename: 'FullyClassifiedDependencyTest.java',
                              path: 'org/wickedsource/dependencytree/example/somepackage/FullyClassifiedDependencyTest.java',
                              packageName: 'org.wickedsource.dependencytree.example.somepackage.FullyClassifiedDependencyTest.java',
                              level: 0,
                              children: [],
                              dependencies: []
                            },
                            {
                              filename: 'InvalidDependencyTest.java',
                              path: 'org/wickedsource/dependencytree/example/somepackage/InvalidDependencyTest.java',
                              packageName: 'org.wickedsource.dependencytree.example.somepackage.InvalidDependencyTest.java',
                              level: 0,
                              children: [],
                              dependencies: []
                            },
                            {
                              filename: 'NotADependencyTest.java',
                              path: 'org/wickedsource/dependencytree/example/somepackage/NotADependencyTest.java',
                              packageName: 'org.wickedsource.dependencytree.example.somepackage.NotADependencyTest.java',
                              level: 0,
                              children: [],
                              dependencies: []
                            }
                          ],
                          dependencies: [
                            'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                            'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                            'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
                            'org/wickedsource/dependencytree/example/CoreTest.java',
                            'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                            'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                            'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java'
                          ]
                        },
                        {
                          filename: 'wildcardpackage',
                          path: 'org/wickedsource/dependencytree/example/wildcardpackage',
                          packageName: 'org.wickedsource.dependencytree.example.wildcardpackage',
                          level: 2,
                          children: [
                            {
                              filename: 'WildcardImport1Test.java',
                              path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                              packageName: 'org.wickedsource.dependencytree.example.wildcardpackage.WildcardImport1Test.java',
                              level: 0,
                              children: [],
                              dependencies: []
                            },
                            {
                              filename: 'WildcardImport2Test.java',
                              path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                              packageName: 'org.wickedsource.dependencytree.example.wildcardpackage.WildcardImport2Test.java',
                              level: 0,
                              children: [],
                              dependencies: []
                            },
                            {
                              filename: 'WildcardImportCircularDependencyTest.java',
                              path: 'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
                              packageName:
                                'org.wickedsource.dependencytree.example.wildcardpackage.WildcardImportCircularDependencyTest.java',
                              level: 0,
                              children: [],
                              dependencies: [
                                'org/wickedsource/dependencytree/example/CoreTest.java'
                              ]
                            }
                          ],
                          dependencies: [
                            'org/wickedsource/dependencytree/example/CoreTest.java'
                          ]
                        }
                      ],
                      dependencies: [
                        'org/wickedsource/dependencytree/example/somepackage/CoreDependencyTest.java',
                        'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                        'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                        'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
                        'org/wickedsource/dependencytree/example/somepackage/NotADependencyTest.java',
                        'org/wickedsource/dependencytree/example/somepackage/FullyClassifiedDependencyTest.java',
                        'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                        'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                        'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
                        'org/wickedsource/dependencytree/example/CoreTest.java',
                        'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                        'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                        'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
                        'org/wickedsource/dependencytree/example/CoreTest.java'
                      ]
                    }
                  ],
                  dependencies: [
                    'org/wickedsource/dependencytree/example/somepackage/CoreDependencyTest.java',
                    'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                    'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                    'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
                    'org/wickedsource/dependencytree/example/somepackage/NotADependencyTest.java',
                    'org/wickedsource/dependencytree/example/somepackage/FullyClassifiedDependencyTest.java',
                    'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                    'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                    'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
                    'org/wickedsource/dependencytree/example/CoreTest.java',
                    'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                    'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                    'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
                    'org/wickedsource/dependencytree/example/CoreTest.java'
                  ]
                }
              ],
              dependencies: [
                'org/wickedsource/dependencytree/example/somepackage/CoreDependencyTest.java',
                'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
                'org/wickedsource/dependencytree/example/somepackage/NotADependencyTest.java',
                'org/wickedsource/dependencytree/example/somepackage/FullyClassifiedDependencyTest.java',
                'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
                'org/wickedsource/dependencytree/example/CoreTest.java',
                'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
                'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
                'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
                'org/wickedsource/dependencytree/example/CoreTest.java'
              ]
            }
          ],
          dependencies: [
            'org/wickedsource/dependencytree/example/somepackage/CoreDependencyTest.java',
            'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
            'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
            'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
            'org/wickedsource/dependencytree/example/somepackage/NotADependencyTest.java',
            'org/wickedsource/dependencytree/example/somepackage/FullyClassifiedDependencyTest.java',
            'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
            'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
            'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
            'org/wickedsource/dependencytree/example/CoreTest.java',
            'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport1Test.java',
            'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImport2Test.java',
            'org/wickedsource/dependencytree/example/wildcardpackage/WildcardImportCircularDependencyTest.java',
            'org/wickedsource/dependencytree/example/CoreTest.java'
          ]
        }
      ],
        dependencies: []
      };

      service.getDependencyTree(1, 'ad48c754991107af9bd0113c45cd009040296f71')
        .then(data => {
          // @ts-ignore
          expect(data.body.filename).toEqual('testSrc');
          // @ts-ignore
          const example = data.body.children[0].children[0].children[0].children[0];
          expect(example.children.length).toBe(3);
        });
      const req = httpTestingController.
        expectOne('http://localhost:8080/analyzers/1/structureMap/ad48c754991107af9bd0113c45cd009040296f71');
      expect(req.request.method).toEqual('GET');
      req.flush(mockResponse);
    });
  });
});
