import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { IRubrique } from 'app/shared/model/rubrique.model';
import { RubriqueService } from './rubrique.service';
import { ILieu } from 'app/shared/model/lieu.model';
import { LieuService } from 'app/entities/lieu';

@Component({
    selector: 'jhi-rubrique-update',
    templateUrl: './rubrique-update.component.html'
})
export class RubriqueUpdateComponent implements OnInit {
    private _rubrique: IRubrique;
    isSaving: boolean;

    lieus: ILieu[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private rubriqueService: RubriqueService,
        private lieuService: LieuService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ rubrique }) => {
            this.rubrique = rubrique;
        });
        this.lieuService.query().subscribe(
            (res: HttpResponse<ILieu[]>) => {
                this.lieus = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.rubrique.id !== undefined) {
            this.subscribeToSaveResponse(this.rubriqueService.update(this.rubrique));
        } else {
            this.subscribeToSaveResponse(this.rubriqueService.create(this.rubrique));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<IRubrique>>) {
        result.subscribe((res: HttpResponse<IRubrique>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    private onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackLieuById(index: number, item: ILieu) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
    get rubrique() {
        return this._rubrique;
    }

    set rubrique(rubrique: IRubrique) {
        this._rubrique = rubrique;
    }
}
