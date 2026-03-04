import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatCardModule } from '@angular/material/card';
import { MatButtonModule } from '@angular/material/button';
import { MatIconModule } from '@angular/material/icon';
import { AuthService } from '../../services/auth.service'; 
import { Router } from '@angular/router';
import { VehicleListComponent } from '../vehicle-list/vehicle-list.component';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, MatCardModule, MatButtonModule, MatIconModule,VehicleListComponent],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent implements OnInit {
  userRole: string | null = '';

  constructor(private auth: AuthService, private router: Router) {}

  ngOnInit() {
    this.userRole = this.auth.getRole();
  }

  logout() {
    this.auth.logout();
    this.router.navigate(['/login']);
  }
}