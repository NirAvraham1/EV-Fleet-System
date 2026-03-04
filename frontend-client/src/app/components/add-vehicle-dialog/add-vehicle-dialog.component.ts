import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { MatDialogRef, MatDialogModule } from '@angular/material/dialog';
import { MatButtonModule } from '@angular/material/button';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { MatSelectModule } from '@angular/material/select';
import { FleetService } from '../../services/fleet.service';

@Component({
  selector: 'app-add-vehicle-dialog',
  standalone: true,
  imports: [
    CommonModule,
    ReactiveFormsModule,
    MatDialogModule,
    MatButtonModule,
    MatFormFieldModule,
    MatInputModule,
    MatSelectModule
  ],
  templateUrl: './add-vehicle-dialog.component.html',
  styleUrls: ['./add-vehicle-dialog.component.scss']
})
export class AddVehicleDialogComponent {
  vehicleForm: FormGroup;

  constructor(
    private fb: FormBuilder,
    private fleetService: FleetService,
    private dialogRef: MatDialogRef<AddVehicleDialogComponent>
  ) {
    this.vehicleForm = this.fb.group({
      model: ['', Validators.required],
      licensePlate: ['', Validators.required],
      status: ['AVAILABLE', Validators.required],
      batteryLevel: [100, [Validators.required, Validators.min(0), Validators.max(100)]],
      location: ['', Validators.required]
    });
  }

  onSubmit() {
    if (this.vehicleForm.valid) {
      this.fleetService.addVehicle(this.vehicleForm.value).subscribe({
        next: (result) => {
          this.dialogRef.close(true);
        },
        error: (err) => console.error('Error adding vehicle', err)
      });
    }
  }

  onCancel() {
    this.dialogRef.close(false);
  }
}