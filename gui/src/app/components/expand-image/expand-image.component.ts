import {
  Component,
  ElementRef,
  EventEmitter,
  HostListener,
  Input,
  OnInit,
  Output,
  ViewChild,
} from '@angular/core';
import { SafeUrl } from '@angular/platform-browser';

@Component({
  selector: 'app-expand-image',
  templateUrl: './expand-image.component.html',
  styleUrls: ['./expand-image.component.scss'],
})
export class ExpandImageComponent implements OnInit {
  @Output() closeEvent = new EventEmitter<boolean>();
  @Input() public image: SafeUrl;
  @ViewChild('dialog') dialog: ElementRef;

  constructor() {}

  ngOnInit(): void {}

  @HostListener('document:mousedown', ['$event'])
  onGlobalClick(event: Event): void {
    if (!this.dialog.nativeElement.contains(event.target)) {
      this.close();
    }
  }

  close() {
    this.closeEvent.emit();
  }
}
