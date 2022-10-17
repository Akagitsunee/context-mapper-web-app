import {
  AfterViewInit,
  ChangeDetectorRef,
  Component,
  ElementRef,
  ViewChild,
} from '@angular/core';
import { DataService } from 'src/app/shared/services/data.service';
import {
  ExportRequest,
  GenerateRequest,
} from '../../shared/models/contextmapper.model';
import { saveAs } from 'file-saver';
import { DomSanitizer, SafeUrl } from '@angular/platform-browser';
import * as ace from 'ace-builds';
import '../../../assets/ace/mode-contextmappingdsl.js';
import '../../../assets/ace/theme-contextmappingdsl.js';
import '../../../assets/ace/worker-contextmappingdsl.js';

@Component({
  selector: 'app-overview',
  templateUrl: './overview.component.html',
  styleUrls: ['./overview.component.scss'],
})
export class OverviewComponent implements AfterViewInit {
  content: string =
    "/* The DDD Cargo sample application modeled in CML. Note that we split the application into multiple bounded contexts. */\r\nContextMap DDDSampleMap {\r\n\tcontains CargoBookingContext\r\n\tcontains VoyagePlanningContext\r\n\tcontains LocationContext\r\n\t\r\n\t/* As Evans mentions in his book (Bounded Context chapter): The voyage planning can be seen as \r\n\t * separated bounded context. However, it still shares code with the booking application (CargoBookingContext).\r\n\t * Thus, they are in a 'Shared-Kernel' relationship.\r\n\t */\r\n\tCargoBookingContext [SK]<->[SK] VoyagePlanningContext\r\n\t\r\n\t/* Note that the splitting of the LocationContext is not mentioned in the original DDD sample of Evans.\r\n\t * However, locations and the management around them, can somehow be seen as a separated concept which is used by other\r\n\t * bounded contexts. But this is just an example, since we want to demonstrate our DSL with multiple bounded contexts.\r\n\t */\r\n\tCargoBookingContext <- LocationContext\r\n\t\r\n\tVoyagePlanningContext <- LocationContext\r\n\t\r\n}\r\n\r\n/* The original booking application context */\r\nBoundedContext CargoBookingContext\r\n\r\n/* We split the Voyage Planning into a separate bounded context as Evans proposes it in his book. */\r\nBoundedContext VoyagePlanningContext\r\n\r\n/* Separate bounded context for managing the locations. */\r\nBoundedContext LocationContext";

  @ViewChild('editor') private editor: ElementRef<HTMLElement>;

  req: GenerateRequest;
  reqExport: ExportRequest;

  selectedGenerator: string = 'CONTEXTMAP';
  selectedFileType: string;

  generators = [{ name: 'CONTEXTMAP', value: 1 }];
  fileTypes: Array<string>;

  image: SafeUrl;
  expand = false;

  constructor(
    private dataService: DataService,
    private sanitizer: DomSanitizer
  ) {}

  ngAfterViewInit(): void {
    this.generateDiagram();
    ace.config.set('fontSize', '18px');

    const aceEditor = ace.edit(this.editor.nativeElement);
    aceEditor.session.setValue(this.content);

    aceEditor.session.setMode('ace/mode/contextmappingdsl');
    aceEditor.setTheme('ace/theme/contextmappingdsl');

    aceEditor.on('change', () => {
      this.content = aceEditor.getValue();
      this.generateDiagram();
    });
  }

  generateDiagram() {
    this.req = {
      generatorType: this.selectedGenerator,
      code: this.escapeString(this.content),
    };
    this.reqExport = {
      exportFormat: 'png',
    };

    this.dataService.generate(this.req).subscribe((data) => {
      this.fileTypes = data.possibleExportFormats;
      this.selectedFileType = this.fileTypes[0];
      this.dataService.export(this.reqExport).subscribe((data) => {
        var unsafeImageUrl = URL.createObjectURL(data);
        this.image = this.sanitizer.bypassSecurityTrustUrl(unsafeImageUrl);
      });
    });
  }

  private escapeString(str: string) {
    return str
      .replace(/\\n/g, '\\n')
      .replace(/\\'/g, "\\'")
      .replace(/\\"/g, '\\"')
      .replace(/\\&/g, '\\&')
      .replace(/\\r/g, '\\r')
      .replace(/\\t/g, '\\t')
      .replace(/\\b/g, '\\b')
      .replace(/\\f/g, '\\f');
  }

  export(): void {
    this.reqExport = {
      exportFormat: this.selectedFileType,
    };

    var name = `${this.selectedGenerator.toLowerCase()}.${
      this.selectedFileType
    }`;

    this.dataService
      .export(this.reqExport)
      .subscribe((blob) => saveAs(blob, name));
  }

  expandImage() {
    this.expand = !this.expand;
  }
}
