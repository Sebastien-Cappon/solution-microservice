import { CommonModule } from "@angular/common";
import { NgModule } from "@angular/core";
import { ReactiveFormsModule } from "@angular/forms";
import { SharedModule } from "../shared/shared.module";
import { NewNoteComponent } from "./components/new-note/new-note.component";
import { NoteListComponent } from "./components/note-list/note-list.component";
import { NoteRoutingModule } from "./note-routing.module";
import { NoteService } from "./services/note.service";
import { EditNoteComponent } from './components/edit-note/edit-note.component';


@NgModule({
  declarations: [
    NewNoteComponent,
    NoteListComponent,
    EditNoteComponent
  ],
  imports: [
    CommonModule,
    NoteRoutingModule,
    SharedModule,
    ReactiveFormsModule
  ],
  exports: [
    NoteListComponent
  ],
  providers: [
    NoteService
  ]
})
export class NoteModule { }
