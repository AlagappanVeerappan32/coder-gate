import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AuthService } from './shared/auth.service';
import { UserService } from './user.service'

@Component({
  selector: 'app-github-login',
  templateUrl: './github-login.component.html',
  styleUrls: ['./github-login.component.css'],
  providers : [
    UserService,
    {provide: 'git_access_token', useValue: localStorage.getItem('github_access_token') },

  ]
})
export class GithubLoginComponent implements OnInit {
  users:any;


  constructor(private authService: AuthService, private route: ActivatedRoute, private router: Router,public userService: UserService) { }
  
  ngOnInit(): void {
    const code = this.route.snapshot.queryParamMap.get('code');
    if (code) {
      this.authService.handleCallback().subscribe((response_: any) => {
        localStorage.setItem("github_access_token",response_["access_token"]);
        this.userService.getUsers().subscribe(response => {
          localStorage.setItem("user",JSON.stringify(response))
      });
        this.router.navigate(['/dashboard']);
      });
     
    }
  }

}
