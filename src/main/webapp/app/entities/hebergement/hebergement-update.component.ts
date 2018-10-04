import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { IHebergement } from 'app/shared/model/hebergement.model';
import { HebergementService } from './hebergement.service';

@Component({
    selector: 'jhi-hebergement-update',
    templateUrl: './hebergement-update.component.html'
})
export class HebergementUpdateComponent implements OnInit {
    private _hebergement: IHebergement;
    isSaving: boolean;

    constructor(private hebergementService: HebergementService, private activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ hebergement }) => {
            this.hebergement = hebergement;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.hebergement.id !== undefined) {
            this.subscribeToSaveResponse(this.hebergementService.update(this.hebergement));
        } else {
            this.subscribeToSaveResponse(this.hebergementService.create(this.hebergement));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IHebergement>>) {
        result.subscribe((res: HttpResponse<IHebergement>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }
    get hebergement() {
        return this._hebergement;
    }

    set hebergement(hebergement: IHebergement) {
        this._hebergement = hebergement;
    }
}
