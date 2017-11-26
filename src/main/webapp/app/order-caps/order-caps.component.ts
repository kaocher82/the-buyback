import {Component, OnInit} from '@angular/core';
import {Http} from "@angular/http";
import {CapConfig} from "../entities/cap-config/cap-config.model";
import {CapOrder} from "../entities/cap-order/cap-order.model";

@Component({
    selector: 'jhi-order-caps',
    templateUrl: './order-caps.component.html'
})
export class OrderCapsComponent implements OnInit {
    initFailed: boolean;
    capConfigs: CapConfig[];

    pickedHull: CapConfig;

    deliveryLocation: string;
    deliveryPrice: number = 0;

    orderComplete: boolean;
    orderFailed: boolean;

    fittingInProgress: boolean;
    pickedBaseFitting: any;
    pickedRefits = [];
    rawFitting = '';

    constructor(private http: Http) {

    }

    ngOnInit(): void {
        this.http.get("/api/cap-configs").subscribe(
            (data) => this.capConfigs = data.json(),
            (err) => this.initFailed = true
        )
    }

    selectConf(name: string) {
        let result = null;
        this.capConfigs.forEach((value) => {
            if (value.typeName === name) {
                result = value;
            }
        });
        return result;
    }

    handleCapSelected(cap: CapConfig) {
        this.pickedHull = cap;
    }

    selectLocation(location: string, price: number) {
        this.deliveryLocation = location;
        this.deliveryPrice = price;
    }

    buy() {
        let order = new CapOrder(
            null,
            null,
            this.pickedHull.typeId,
            this.pickedHull.typeName,
            this.pickedHull.price,
            null,
            this.deliveryLocation,
            this.deliveryPrice,
            null
        );
        this.http.post("/api/cap-orders", order).subscribe(
            (data) => this.orderComplete,
            (err) => this.orderFailed
        )
    }

    highlightIfSelected(fitting) {
        if (fitting === this.pickedBaseFitting) {
            return 'highlight';
        }
    }

    isSelected(fitting) {
        if (fitting === this.pickedBaseFitting) {
            return true;
        } else {
            for (let i = 0; i < this.pickedRefits.length; i++) {
                if (fitting === this.pickedRefits[i]) {
                    return true;
                }
            }
        }
        return false;
    }

    selectBaseFit(fitting: any) {
        this.pickedBaseFitting = fitting;
        this.rawFitting = fitting.content;
        this.pickedRefits = [];
        this.fittingInProgress = true;
    }

    addRefit(fitting: any) {
        this.pickedRefits.push(fitting);
        if (this.rawFitting) {
            this.rawFitting += '\n\n';
        }
        this.rawFitting += fitting.content;
        this.fittingInProgress = true;
    }

    startFittingInProgress() {
        this.fittingInProgress = true;
        setTimeout(function() {
            window.scrollTo(0,document.body.scrollHeight);
            document.getElementById("fittingArea").focus();
        }, 100);
    }

    pickHull(hull: any) {
        this.pickedHull = hull;
    }

    baseFittings = [
        {
            'name': 'Naglfar, Brave Buffer Fit T1',
            'shortName': 'Buffer Fit T1',
            'link': '',
            'price': 1405480294,
            'content': 'An Item x3\nTwo Items'
        },
        {
            'name': 'Naglfar, Brave Buffer Fit T2',
            'shortName': 'Buffer Fit T2',
            'link': '',
            'price': 1405480294,
            'content': 'An Item x3\nTwo Items'
        }
    ]

    refits = [
        {
            'name': 'Naglfar, Active Refit T1',
            'shortName': 'Active Refit T1',
            'link': '',
            'price': 1405480294,
            'content': 'An Item x3\nTwo Items'
        },
        {
            'name': 'Naglfar, Active Refit T2',
            'shortName': 'Active Refit T2',
            'link': '',
            'price': 1405480294,
            'content': 'An Item x3\nTwo Items'
        },
        {
            'name': 'Naglfar, Cap Refit',
            'shortName': 'Cap Refit',
            'link': '',
            'price': 231749264,
            'content': 'An Item x3\nTwo Items'
        },
        {
            'name': 'Naglfar, Travel Fit',
            'shortName': 'Travel Refit',
            'link': '',
            'price': 51749264,
            'content': 'An Item x3\nTwo Items'
        }
    ]

    dreads = [
        {
            'name': 'Moros',
            'typeId': 19724,
            'price': 1900000000
        },
        {
            'name': 'Pheonix',
            'typeId': 19726,
            'price': 1900000000
        },
        {
            'name': 'Naglfar',
            'typeId': 19722,
            'price': 1900000000
        },
        {
            'name': 'Revelation',
            'typeId': 19720,
            'price': 1900000000
        }
    ]
    carriers = [
        {
            'name': 'Thanatos',
            'typeId': 23911
        },
        {
            'name': 'Chimera',
            'typeId': 23915
        },
        {
            'name': 'Nidhoggur',
            'typeId': 24483
        },
        {
            'name': 'Archon',
            'typeId': 23757
        }
    ]
    supers = [
        {
            'name': 'Nyx',
            'typeId': 23913
        },
        {
            'name': 'Wyvern',
            'typeId': 23917
        },
        {
            'name': 'Hel',
            'typeId': 22852
        },
        {
            'name': 'Aeon',
            'typeId': 23919
        }
    ]
}
