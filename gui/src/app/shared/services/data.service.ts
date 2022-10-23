import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {
  ExportRequest,
  GenerateRequest,
  GenerateResponse,
} from '../models/contextmapper.model';

const httpOptions = {
  headers: new HttpHeaders({
    'Content-Type': 'application/json',
  }),
};

@Injectable({
  providedIn: 'root',
})
export class DataService {
  private BASE_URL = 'http://localhost:8080';
  private GENERATE_URL = this.BASE_URL + '/generate';
  private EXPORT_URL = this.BASE_URL + '/export';

  constructor(private http: HttpClient) {}

  public generate(req: GenerateRequest) {
    return this.http.post<GenerateResponse>(
      this.GENERATE_URL,
      req,
      httpOptions
    );
  }

  public export(req: ExportRequest): Observable<Blob> {
    return this.http.get(this.EXPORT_URL + '/' + req.exportFormat, {
      responseType: 'blob',
    });
  }
}
