import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IConfort } from 'app/shared/model/confort.model';
import { Principal } from 'app/core';
import { ConfortService } from './confort.service';

@Component({
    selector: 'jhi-confort',
    templateUrl: './confort.component.html'
})
export class ConfortComponent implements OnInit, OnDestroy {
    conforts: IConfort[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private confortService: ConfortService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {}

    loadAll() {
        this.confortService.query().subscribe(
            (res: HttpResponse<IConfort[]>) => {
                this.conforts = res.body;
            },
            (res: HttpErrorResponse) => this.onError(res.message)
        );
    }

    ngOnInit() {
        this.loadAll();
        this.principal.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInConforts();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IConfort) {
        return item.id;
    }

    registerChangeInConforts() {
        this.eventSubscriber = this.eventManager.subscribe('confortListModification', response => this.loadAll());
    }

    private onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
