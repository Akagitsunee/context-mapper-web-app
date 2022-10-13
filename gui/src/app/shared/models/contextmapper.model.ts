export interface GenerateRequest {
  generatorType: string;
  code: string;
}

export interface GenerateResponse {
  possibleExportFormats: Array<string>;
}

export interface ExportRequest {
  exportFormat: string;
}
