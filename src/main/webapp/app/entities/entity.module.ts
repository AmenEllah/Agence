import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { AgenceVoyageModule } from './voyage/voyage.module';
import { AgenceReservationModule } from './reservation/reservation.module';
import { AgenceConfortModule } from './confort/confort.module';
import { AgenceHebergementModule } from './hebergement/hebergement.module';
import { AgenceRubriqueModule } from './rubrique/rubrique.module';
import { AgenceLieuModule } from './lieu/lieu.module';
import { AgenceEnvironnementModule } from './environnement/environnement.module';
import { AgenceImageModule } from './image/image.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        AgenceVoyageModule,
        AgenceReservationModule,
        AgenceConfortModule,
        AgenceHebergementModule,
        AgenceRubriqueModule,
        AgenceLieuModule,
        AgenceEnvironnementModule,
        AgenceImageModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class AgenceEntityModule {}
