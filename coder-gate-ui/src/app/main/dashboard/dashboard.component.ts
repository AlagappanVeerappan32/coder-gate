import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Repository } from '../repository';
import { RepositoryService } from '../repository.service';
import {HttpEvent, HttpHandler, HttpInterceptor,HttpRequest,HttpResponse,HttpErrorResponse} from '@angular/common/http';


@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent {

  public repositories: Repository[];
  constructor(private repositoryService : RepositoryService ) { }

  public getRepositories(): void{
    this.repositoryService.getRepositories().subscribe(
    (response: Repository[]) => {
    this.repositories = response;
    },
    (error: HttpErrorResponse) => {
    alert(error.message);
    }
    );
  }

  public selectedRepo?: string;
  public showThresholdView = false;

  myForm?: FormGroup;


  constructor(private fb: FormBuilder) {
  }

  ngOnInit() {
  this.getRepositories();
    this.myForm = this.fb.group({
      repository: ['', Validators.required],
      smellDensity: ['', [Validators.required]],
      message: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.myForm?.valid) {
      // Do something with the form data here
      console.log(this.myForm.value);
    }
  }


  public ELEMENT_DATA = [
    {
      title: "Java language server",
      description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam bibendum lacinia ligula. Donec dictum neque tincidunt lacus rhoncus, in elementum nisi pharetra. Suspendisse velit risus, mollis qui",
      lastUpdatedOn: "27th January 2023",
      health: 48,
      tag: "Featured"
    },
    {
      title: "Postman",
      description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam bibendum lacinia ligula. Donec dictum neque tincidunt lacus rhoncus, in elementum nisi pharetra. Suspendisse velit risus, mollis qui",
      lastUpdatedOn: "9th February 2023",
      health: 90,
      tag: "Require attention"
    },
    {
      title: "Apache Maven",
      description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam bibendum lacinia ligula. Donec dictum neque tincidunt lacus rhoncus, in elementum nisi pharetra. Suspendisse velit risus, mollis qui",
      lastUpdatedOn: "2nd March 2023",
      health: 21,
      tag: "New"
    },
    {
      title: "Java language server",
      description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam bibendum lacinia ligula. Donec dictum neque tincidunt lacus rhoncus, in elementum nisi pharetra. Suspendisse velit risus, mollis qui",
      lastUpdatedOn: "27th January 2023",
      health: 48,
      tag: "Featured"
    },
    {
      title: "Postman",
      description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam bibendum lacinia ligula. Donec dictum neque tincidunt lacus rhoncus, in elementum nisi pharetra. Suspendisse velit risus, mollis qui",
      lastUpdatedOn: "9th February 2023",
      health: 90,
      tag: "Require attention"
    }
    ,
    {
      title: "Apache Maven",
      description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam bibendum lacinia ligula. Donec dictum neque tincidunt lacus rhoncus, in elementum nisi pharetra. Suspendisse velit risus, mollis qui",
      lastUpdatedOn: "2nd March 2023",
      health: 21,
      tag: "New"
    }
    ,
    {
      title: "Apache Maven",
      description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam bibendum lacinia ligula. Donec dictum neque tincidunt lacus rhoncus, in elementum nisi pharetra. Suspendisse velit risus, mollis qui",
      lastUpdatedOn: "2nd March 2023",
      health: 21,
      tag: "New"
    }
    ,
    {
      title: "Apache Maven",
      description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam bibendum lacinia ligula. Donec dictum neque tincidunt lacus rhoncus, in elementum nisi pharetra. Suspendisse velit risus, mollis qui",
      lastUpdatedOn: "2nd March 2023",
      health: 21,
      tag: "New"
    }
    ,
    {
      title: "Apache Maven",
      description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam bibendum lacinia ligula. Donec dictum neque tincidunt lacus rhoncus, in elementum nisi pharetra. Suspendisse velit risus, mollis qui",
      lastUpdatedOn: "2nd March 2023",
      health: 21,
      tag: "New"
    }
    ,
    {
      title: "Apache Maven",
      description: "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nam bibendum lacinia ligula. Donec dictum neque tincidunt lacus rhoncus, in elementum nisi pharetra. Suspendisse velit risus, mollis qui",
      lastUpdatedOn: "2nd March 2023",
      health: 21,
      tag: "New"
    }
  ];

  displayedColumns: string[] = ['name', 'Last updated', 'health'];
  dataSource = this.ELEMENT_DATA;

  public onRepoClick(repo: string) {
    this.selectedRepo = repo;
    this.showThresholdView = true;
  }
}
