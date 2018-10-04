import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRubrique } from 'app/shared/model/rubrique.model';
import { Principal } from 'app/core';
import { RubriqueService } from './rubrique.service';

@Component({
    selector: 'jhi-rubrique',
    templateUrl: './rubrique.component.html'
})
export class RubriqueComponent implements OnInit, OnDestroy {
    rubriques: IRubrique[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private rubriqueService: RubriqueService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.rubriqueService.query().subscribe(
            (res: HttpResponse<IRubrique[]>) => {
                this.rubriques = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInRubriques();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRubrique) {
        return item.id;
    }

    registerChangeInRubriques() {
        this.eventSubscriber = this.eventManager.subscribe('rubriqueListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
