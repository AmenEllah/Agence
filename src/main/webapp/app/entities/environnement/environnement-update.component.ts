import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IEnvironnement } from 'app/shared/model/environnement.model';
import { EnvironnementService } from './environnement.service';

@Component({
    selector: 'jhi-environnement-update',
    templateUrl: './environnement-update.component.html'
})
export class EnvironnementUpdateComponent implements OnInit {
    private _environnement: IEnvironnement;
    isSaving: boolean;

    constructor(private environnementService: EnvironnementService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ environnement }) => {
            this.environnement = environnement;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.environnement.id !== undefined) {
            this.subscribeToSaveResponse(this.environnementService.update(this.environnement));
        } else {
            this.subscribeToSaveResponse(this.environnementService.create(this.environnement));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IEnvironnement>>) {
        result.subscribe((res: HttpResponse<IEnvironnement>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get environnement() {
        return this._environnement;
    }

    set environnement(environnement: IEnvironnement) {
        this._environnement = environnement;
    }
}
