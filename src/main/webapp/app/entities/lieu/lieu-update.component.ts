import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { JhiAlertService } from 'ng-jhipster';

import { ILieu } from 'app/shared/model/lieu.model';
import { LieuService } from './lieu.service';
import { IRubrique } from 'app/shared/model/rubrique.model';
import { RubriqueService } from 'app/entities/rubrique';

@Component({
    selector: 'jhi-lieu-update',
    templateUrl: './lieu-update.component.html'
})
export class LieuUpdateComponent implements OnInit {
    private _lieu: ILieu;
    isSaving: boolean;

    rubriques: IRubrique[];

    constructor(
        private jhiAlertService: JhiAlertService,
        private lieuService: LieuService,
        private rubriqueService: RubriqueService,
        private activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ lieu }) => {
            this.lieu = lieu;
        });
        this.rubriqueService.query().subscribe(
            (res: HttpResponse<IRubrique[]>) => {
                this.rubriques = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.lieu.id !== undefined) {
            this.subscribeToSaveResponse(this.lieuService.update(this.lieu));
        } else {
            this.subscribeToSaveResponse(this.lieuService.create(this.lieu));
        }
    }

    private subscribeToSaveResponse(result: Observable<HttpResponse<ILieu>>) {
        result.subscribe((res: HttpResponse<ILieu>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackRubriqueById(index: number, item: IRubrique) {
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
    get lieu() {
        return this._lieu;
    }

    set lieu(lieu: ILieu) {
        this._lieu = lieu;
    }
}
