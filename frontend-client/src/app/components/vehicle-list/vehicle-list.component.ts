import { Component, OnInit, ChangeDetectorRef, NgZone } from '@angular/core';
import { CommonModule } from '@angular/common';
import { MatTableModule } from '@angular/material/table';
import { MatCardModule } from '@angular/material/card';
import { MatIconModule } from '@angular/material/icon';
import { MatButtonModule } from '@angular/material/button';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { MatPaginatorModule, PageEvent } from '@angular/material/paginator';
import { FleetService, Vehicle } from '../../services/fleet.service';
import { AuthService } from '../../services/auth.service';
import { AddVehicleDialogComponent } from '../add-vehicle-dialog/add-vehicle-dialog.component';

@Component({
  selector: 'app-vehicle-list',
  standalone: true,
  imports: [
    CommonModule, 
    MatTableModule, 
    MatCardModule, 
    MatIconModule, 
    MatButtonModule,
    MatDialogModule,
    MatPaginatorModule
  ],
  templateUrl: './vehicle-list.component.html',
  styleUrls: ['./vehicle-list.component.scss']
})
export class VehicleListComponent implements OnInit {
  vehicles: Vehicle[] = [];
  displayedColumns: string[] = []; 
  
  totalVehicles = 0;
  pageSize = 10;
  currentPage = 0;
  isAdmin = false;

  constructor(
    private fleetService: FleetService, 
    private authService: AuthService,
    private dialog: MatDialog,
    private cdr: ChangeDetectorRef,
    private zone: NgZone
  ) {}

  ngOnInit(): void {
    this.checkRole();
    console.log('Vehicle List Initialized');
    this.loadVehicles();
  }

  checkRole() {
    this.isAdmin = this.authService.isAdmin();
    console.log('User role detected as Admin:', this.isAdmin);

    if (this.isAdmin) {
      this.displayedColumns = ['model', 'licensePlate', 'batteryLevel', 'status', 'location', 'actions'];
    } else {
      this.displayedColumns = ['model', 'licensePlate', 'batteryLevel', 'status', 'location'];
    }
  }

  loadVehicles() {
    this.fleetService.getAllVehicles(this.currentPage, this.pageSize).subscribe({
      next: (response) => {
        console.log('Data received from server:', response);
        this.zone.run(() => {
          this.vehicles = response.content;
          this.totalVehicles = response.totalElements;
          this.cdr.detectChanges();
        });
      },
      error: (err) => {
        console.error('Failed to load vehicles', err);
        setTimeout(() => this.loadVehicles(), 500); 
      }
    });
  }

  onPageChange(event: PageEvent) {
    this.currentPage = event.pageIndex;
    this.pageSize = event.pageSize;
    this.loadVehicles();
  }

  openAddVehicleDialog() {
    const dialogRef = this.dialog.open(AddVehicleDialogComponent);

    dialogRef.afterClosed().subscribe(result => {
      if (result === true) {
        this.currentPage = 0; 
        this.loadVehicles();
      }
    });
  }
  
  deleteVehicle(id: string) {
    if (!this.isAdmin) {
      alert('Access Denied: Only Admins can delete vehicles.');
      return;
    }

    if(confirm('Are you sure?')) {
      this.fleetService.deleteVehicle(id).subscribe(() => {
        this.loadVehicles();
      });
    }
  }

  toggleStatus(vehicle: Vehicle) {
    if (!this.isAdmin) {
      alert('Access Denied: Only Admins can change vehicle status.');
      return;
    }

    if (!vehicle.id) return;

    const newStatus = vehicle.status === 'AVAILABLE' ? 'CHARGING' : 'AVAILABLE';
    
    this.fleetService.updateVehicleStatus(vehicle.id, newStatus).subscribe({
      next: () => {
        console.log('Status updated successfully');
        this.loadVehicles();
      },
      error: (err) => console.error('Error updating status', err)
    });
  }

  getStatusColor(status: string): string {
    switch(status) {
      case 'AVAILABLE': return 'green';
      case 'IN_USE': return 'blue';
      case 'CHARGING': return 'orange';
      case 'MAINTENANCE': return 'red';
      default: return 'black';
    }
  }
}