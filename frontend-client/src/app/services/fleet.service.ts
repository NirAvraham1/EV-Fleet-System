import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { AuthService } from './auth.service';
import { environment } from '../../environments/environment';
import { Observable } from 'rxjs';

export interface Vehicle {
  id?: string;
  licensePlate: string;
  model: string;
  status: 'AVAILABLE' | 'CHARGING' | 'IN_USE' | 'MAINTENANCE';
  batteryLevel: number;
  location: string;
}

export interface PageResponse<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

@Injectable({
  providedIn: 'root'
})
export class FleetService {
  private apiUrl = environment.fleetUrl;

  constructor(private http: HttpClient, private auth: AuthService) {}

  private getHeaders(): HttpHeaders {
    const token = this.auth.getToken();
    return new HttpHeaders({
      'Authorization': `Bearer ${token}`,
      'Content-Type': 'application/json'
    });
  }

  getAllVehicles(page: number, size: number): Observable<PageResponse<Vehicle>> {
    let params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<PageResponse<Vehicle>>(this.apiUrl, { headers: this.getHeaders(), params });
  }

  addVehicle(vehicle: Vehicle): Observable<Vehicle> {
    return this.http.post<Vehicle>(this.apiUrl, vehicle, { headers: this.getHeaders() });
  }
  
  deleteVehicle(id: string): Observable<void> {
    return this.http.delete<void>(`${this.apiUrl}/${id}`, { headers: this.getHeaders() });
  }

  updateVehicleStatus(id: string, status: string): Observable<Vehicle> {
    return this.http.patch<Vehicle>(
      `${this.apiUrl}/${id}/status`, 
      {}, 
      { headers: this.getHeaders(), params: { status: status } }
    );
  }
}