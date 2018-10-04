import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IConfort } from 'app/shared/model/confort.model';
import { ConfortService } from './confort.service';

@Component({
    selector: 'jhi-confort-update',
    templateUrl: './confort-update.component.html'
})
export class ConfortUpdateComponent implements OnInit {
    private _confort: IConfort;
    isSaving: boolean;

    constructor(private confortService: ConfortService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ confort }) => {
            this.confort = confort;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.confort.id !== undefined) {
            this.subscribeToSaveResponse(this.confortService.update(this.confort));
        } else {
            this.subscribeToSaveResponse(this.confortService.create(this.confort));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IConfort>>) {
        result.subscribe((res: HttpResponse<IConfort>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get confort() {
        return this._confort;
    }

    set confort(confort: IConfort) {
        this._confort = confort;
    }
}
